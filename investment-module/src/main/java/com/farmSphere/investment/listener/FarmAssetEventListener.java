package com.farmSphere.investment.listener;

import com.farmSphere.core.enums.ASSET_STATUS;
import com.farmSphere.core.event.farming.FarmCycleHarvestedEvent;
import com.farmSphere.investment.data.repository.FarmAssetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FarmAssetEventListener {
    private final FarmAssetRepository assetRepository;

    @EventListener
    public void onFarmCycleHarvested(FarmCycleHarvestedEvent event) {
        assetRepository.findByCropPlanId(event.getCropPlanId()).ifPresent(asset -> {
            if (asset.getStatus() == ASSET_STATUS.PAID_OUT) {
                log.info("Asset {} already distributed — skipping ROI calc", asset.getId());
                return;
            }
            // ROI efficiency calculation, if farmer expected 2000kg but got 1800kg efficiency = 1800 / 2000 = 0.90 (90%),actualROI  = 20% × 0.90 = 18%

            float expectedYield = asset.getExpectedYieldKg();
            if (expectedYield <= 0) {
                log.warn("Asset {} has zero expectedYieldKg — " + "cannot calculate ROI", asset.getId());
                return;
            }

            float efficiency = event.getTotalPrimaryYieldKg() / expectedYield;

            float actualROI = asset.getExpectedROI() * efficiency;
            if (actualROI < 0) actualROI = 0;

            asset.setActualROI(actualROI);
            asset.setStatus(ASSET_STATUS.CLOSED);
            assetRepository.save(asset);

            log.info("Asset {} actualROI set to {}% — ready for distribution", asset.getId(), actualROI);
        });
    }
}