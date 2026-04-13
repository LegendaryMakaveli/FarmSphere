package com.farmSphere.investment.service;


import com.farmSphere.core.dto.CropPlanSummary;
import com.farmSphere.core.enums.ASSET_STATUS;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.infrastructure.security.SecurityUtils;
import com.farmSphere.investment.data.model.FarmAsset;
import com.farmSphere.investment.data.repository.FarmAssetRepository;
import com.farmSphere.investment.dto.request.CreateAssetRequest;
import com.farmSphere.investment.dto.request.UpdateAssetPriceRequest;
import com.farmSphere.investment.dto.response.FarmAssetResponse;
import com.farmSphere.investment.dto.response.SuggestedROIResponse;
import com.farmSphere.investment.feign.FarmingClient;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.farmSphere.investment.util.Validations.getFarmAsset;

@Service
@RequiredArgsConstructor
public class FarmAssetServiceImplementation implements FarmAssetService{
    private final FarmAssetRepository assetRepository;
    private final FarmingClient farmingClient;


    @Override
    public FarmAssetResponse createAsset(CreateAssetRequest request) {
        SecurityUtils.requireAdmin();
        if (assetRepository.existsByCropPlanId(request.getCropPlanId())) throw new DomainException("A FarmAsset already exists for this CropPlan", 409);
        if (request.getFundingDeadline().isBefore(LocalDate.now())) throw new DomainException("Funding deadline must be in the future", 400);

        SuggestedROIResponse suggested = calculateSuggestedROI(
                request.getCropPlanId(),
                request.getMarketPricePerUnit(),
                request.getMarketUnit(),
                request.getTotalUnits(),
                request.getUnitPrice()
        );

        float finalROI = request.getOverrideROI() != null ? request.getOverrideROI() : suggested.getSuggestedROI();

        FarmAsset asset = getFarmAsset(request);
        asset.setExpectedYieldKg(suggested.getTotalExpectedYieldKg());
        asset.setExpectedROI(finalROI);


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



    public SuggestedROIResponse calculateSuggestedROI(Long cropPlanId, BigDecimal marketPricePerUnit, String marketUnit, int totalUnits, BigDecimal unitPrice) {
        CropPlanSummary cropPlan = farmingClient.getCropPlanSummary(cropPlanId);

        float totalExpectedYield = (float) cropPlan.getItems().stream()
                .filter(item -> "PRIMARY".equals(item.getRole()))
                .mapToDouble(CropPlanSummary.CropPlanItemSummary::getExpectedYield)
                .sum();
        if (totalExpectedYield <= 0) throw new DomainException("No PRIMARY crop yield found in this CropPlan", 400);

        BigDecimal projectedRevenue = marketPricePerUnit.multiply(BigDecimal.valueOf(totalExpectedYield));
        BigDecimal totalInvestment = unitPrice.multiply(BigDecimal.valueOf(totalUnits));

        if (totalInvestment.compareTo(BigDecimal.ZERO) == 0) throw new DomainException("Total investment cannot be zero", 400);

        BigDecimal profit = projectedRevenue.subtract(totalInvestment);
        BigDecimal suggestedROI = profit.divide(totalInvestment, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        String primaryCropName = cropPlan.getItems().stream()
                .filter(item -> "PRIMARY".equals(item.getRole()))
                .map(CropPlanSummary.CropPlanItemSummary::getCropName)
                .findFirst()
                .orElse("Unknown");

        return SuggestedROIResponse.builder()
                .cropPlanId(cropPlanId)
                .plotId(cropPlan.getPlotId())
                .primaryCropName(primaryCropName)
                .totalExpectedYieldKg(totalExpectedYield)
                .marketPricePerUnit(marketPricePerUnit)
                .marketUnit(marketUnit)
                .projectedRevenue(projectedRevenue)
                .totalInvestment(totalInvestment)
                .suggestedROI(suggestedROI.floatValue())
                .note(suggestedROI.floatValue() < 0
                        ? "WARNING: Projected revenue is less than total investment"
                        : "Profitable — looks good to tokenize")
                .build();
    }
}
