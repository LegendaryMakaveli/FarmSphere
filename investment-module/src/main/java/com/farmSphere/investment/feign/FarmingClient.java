package com.farmSphere.investment.feign;

import com.farmSphere.core.dto.CropPlanSummary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Component
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

        return restTemplate.getForObject(url, CropPlanSummary.class);
    }
}