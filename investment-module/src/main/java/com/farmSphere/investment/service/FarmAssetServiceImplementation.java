package com.farmSphere.investment.service;


import com.farmSphere.core.enums.ASSET_STATUS;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.infrastructure.security.SecurityUtils;
import com.farmSphere.investment.data.model.FarmAsset;
import com.farmSphere.investment.data.repository.FarmAssetRepository;
import com.farmSphere.investment.dto.request.CreateAssetRequest;
import com.farmSphere.investment.dto.request.UpdateAssetPriceRequest;
import com.farmSphere.investment.dto.response.FarmAssetResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.farmSphere.investment.util.Validations.getFarmAsset;

@Service
@RequiredArgsConstructor
public class FarmAssetServiceImplementation implements FarmAssetService{
    private final FarmAssetRepository assetRepository;

    @Override
    public FarmAssetResponse createAsset(@NonNull @NotBlank CreateAssetRequest request) {
        SecurityUtils.requireAdmin();
        if (assetRepository.existsByCropPlanId(request.getCropPlanId())) throw new DomainException("A FarmAsset already exists for this CropPlan", 409);
        if (request.getFundingDeadline().isBefore(LocalDate.now())) throw new DomainException("Funding deadline must be in the future", 400);

        FarmAsset asset = getFarmAsset(request);

        return FarmAssetResponse.from(assetRepository.save(asset));
    }

    @Override
    public FarmAssetResponse updatePrice(Long assetId, @Valid UpdateAssetPriceRequest request) {
        SecurityUtils.requireAdmin();
        FarmAsset asset = assetRepository.findById(assetId).orElseThrow(() -> new DomainException("Asset not found", 404));
        if (asset.getStatus() != ASSET_STATUS.OPEN) throw new DomainException("Price can only be updated while asset is OPEN", 400);
        asset.setUnitPrice(request.getUnitPrice());
        asset.setUpdatedAt(LocalDateTime.now());

        return FarmAssetResponse.from(assetRepository.save(asset));
    }

    @Override
    public FarmAssetResponse closeFunding(Long assetId) {
        SecurityUtils.requireAdmin();
        FarmAsset asset = assetRepository.findById(assetId).orElseThrow(() -> new DomainException("Asset not found", 404));
        if (asset.getStatus() != ASSET_STATUS.OPEN) throw new DomainException("Asset is not OPEN", 400);

        asset.setStatus(ASSET_STATUS.CLOSED);
        asset.setUpdatedAt(LocalDateTime.now());

        return FarmAssetResponse.from(assetRepository.save(asset));    }

    @Transactional
    @Override
    public void autoCloseExpiredAssets() {
        List<FarmAsset> openAssets = assetRepository.findAllByStatus(ASSET_STATUS.OPEN);
        openAssets.stream()
                .filter(asset -> asset.getFundingDeadline().isBefore(LocalDate.now()))
                .forEach(farmAsset -> {
                    farmAsset.setStatus(ASSET_STATUS.CLOSED);
                    farmAsset.setUpdatedAt(LocalDateTime.now());
                    assetRepository.save(farmAsset);
                });
    }

    @Transactional
    @Override
    public List<FarmAssetResponse> getOpenAssets() {
        SecurityUtils.requireInvestor();
        return assetRepository.findAllByStatus(ASSET_STATUS.OPEN).stream().map(FarmAssetResponse::from).toList();
    }

    @Override
    public List<FarmAssetResponse> getAllAssets() {
        SecurityUtils.requireAdmin();
        return assetRepository.findAll().stream().map(FarmAssetResponse::from).toList();
    }

    @Override
    public FarmAssetResponse getAssetById(Long assetId) {
        SecurityUtils.requireInvestor();
        return assetRepository.findById(assetId)
                .map(FarmAssetResponse::from)
                .orElseThrow(() -> new DomainException("Asset not found", 404));
    }
}
