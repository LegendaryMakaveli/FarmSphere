package com.farmSphere.investment.feign;

import com.farmSphere.core.dto.CropPlanSummary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import com.farmSphere.infrastructure.exception.DomainException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FarmingClient {

    private final RestTemplate restTemplate;

    @Value("${services.farming.url}")
    private String farmingServiceUrl;

    public FarmingClient() {
        this.restTemplate = new RestTemplate();
    }

    public CropPlanSummary getCropPlanSummary(Long cropPlanId) {
        String url = farmingServiceUrl
                + "/internal/farming/crop-plans/"
                + cropPlanId
                + "/summary";

        try {
            log.info("Fetching crop plan summary from: {}", url);
            return restTemplate.getForObject(url, CropPlanSummary.class);
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Crop plan not found: {}", cropPlanId);
            throw new DomainException("Crop plan with ID " + cropPlanId + " not found in farming module", 404);
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden e) {
            log.error("Security error calling farming service: {}", e.getMessage());
            throw new DomainException("Module-to-module communication blocked: Access Denied (Check SecurityConfig)", 403);
        } catch (RestClientException e) {
            log.error("Network error calling farming service at {}: {}. Root cause: {}", url, e.getMessage(), 
                    e.getCause() != null ? e.getCause().getMessage() : "Unknown");
            throw new DomainException("Failed to communicate with farming service: " + e.getMessage(), 500);
        }
    }
}