package com.farmSphere.scheduler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class KeepAliveScheduler {
    @Value("${app.self-ping-url:http://localhost:8080/actuator/health}")
    private String selfPingUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedDelay = 840_000) // every 14 minutes
    public void keepAlive() {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(selfPingUrl, String.class);
            log.debug("Self-ping successful: {}", response.getStatusCode());
        } catch (Exception e) {
            log.warn("Self-ping failed: {}", e.getMessage());
        }
    }
}
