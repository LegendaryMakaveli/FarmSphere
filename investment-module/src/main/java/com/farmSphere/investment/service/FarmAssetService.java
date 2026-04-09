package com.farmSphere.investment.service;

import com.farmSphere.investment.dto.request.CreateAssetRequest;
import com.farmSphere.investment.dto.request.UpdateAssetPriceRequest;
import com.farmSphere.investment.dto.response.FarmAssetResponse;

import java.util.List;

public interface FarmAssetService {
    FarmAssetResponse createAsset(CreateAssetRequest request);
    FarmAssetResponse updatePrice(Long assetId, UpdateAssetPriceRequest request);
    FarmAssetResponse closeFunding(Long assetId);
    void autoCloseExpiredAssets();
    List<FarmAssetResponse> getOpenAssets();
    List<FarmAssetResponse> getAllAssets();
    FarmAssetResponse getAssetById(Long assetId);

}
