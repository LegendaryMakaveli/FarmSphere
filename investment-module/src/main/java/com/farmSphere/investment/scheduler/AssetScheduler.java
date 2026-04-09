package com.farmSphere.investment.scheduler;

import com.farmSphere.investment.service.FarmAssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@EnableScheduling
public class AssetScheduler {

    private final FarmAssetService assetService;

    @Scheduled(cron = "0 0 0 * * *")
    public void closeExpiredAssets() {
        assetService.autoCloseExpiredAssets();
    }
}
