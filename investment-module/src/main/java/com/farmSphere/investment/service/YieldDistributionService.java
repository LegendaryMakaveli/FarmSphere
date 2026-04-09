package com.farmSphere.investment.service;

import com.farmSphere.investment.dto.request.RecordHarvestRequest;
import com.farmSphere.investment.dto.response.FarmAssetResponse;
import com.farmSphere.investment.dto.response.YieldDistributionResponse;

public interface YieldDistributionService {
    FarmAssetResponse recordHarvest(Long assetId, RecordHarvestRequest request, Long adminId);
    YieldDistributionResponse distributeROI(Long assetId, Long adminId);
    YieldDistributionResponse getDistribution(Long assetId);
}
