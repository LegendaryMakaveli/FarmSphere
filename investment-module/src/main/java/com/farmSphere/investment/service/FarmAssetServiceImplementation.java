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
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.farmSphere.core.enums.YIELD_UNIT;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.farmSphere.investment.util.Validations.getFarmAsset;

@Service
@RequiredArgsConstructor
@Slf4j
public class FarmAssetServiceImplementation implements FarmAssetService {
    private final FarmAssetRepository assetRepository;
    private final FarmingClient farmingClient;

    @Override
    public FarmAssetResponse createAsset(CreateAssetRequest request) {
        SecurityUtils.requireAdmin();
        if (assetRepository.existsByCropPlanId(request.getCropPlanId()))
            throw new DomainException("A FarmAsset already exists for this CropPlan", 409);
        if (request.getFundingDeadline().isBefore(LocalDate.now()))
            throw new DomainException("Funding deadline must be in the future", 400);

        SuggestedROIResponse suggested = calculateSuggestedROI(
                request.getCropPlanId(),
                request.getMarketPricePerUnit(),
                request.getMarketUnit(),
                request.getTotalUnits(),
                request.getUnitPrice());

        float finalROI = request.getOverrideROI() != null ? request.getOverrideROI() : suggested.getSuggestedROI();

        log.info("Creating asset for crop plan: {}", request.getCropPlanId());
        FarmAsset asset = getFarmAsset(request);
        asset.setExpectedYieldKg(suggested.getTotalExpectedYieldKg());
        asset.setExpectedROI(finalROI);

        FarmAsset saved = assetRepository.save(asset);
        log.info("Asset created successfully with ID: {}", saved.getId());
        return FarmAssetResponse.from(saved);
    }

    @Override
    public FarmAssetResponse updatePrice(Long assetId, @Valid UpdateAssetPriceRequest request) {
        SecurityUtils.requireAdmin();
        FarmAsset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new DomainException("Asset not found", 404));
        if (asset.getStatus() != ASSET_STATUS.OPEN)
            throw new DomainException("Price can only be updated while asset is OPEN", 400);
        asset.setUnitPrice(request.getUnitPrice());
        asset.setUpdatedAt(LocalDateTime.now());

        return FarmAssetResponse.from(assetRepository.save(asset));
    }

    @Override
    public FarmAssetResponse closeFunding(Long assetId) {
        SecurityUtils.requireAdmin();
        FarmAsset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new DomainException("Asset not found", 404));
        if (asset.getStatus() != ASSET_STATUS.OPEN)
            throw new DomainException("Asset is not OPEN", 400);

        asset.setStatus(ASSET_STATUS.CLOSED);
        asset.setUpdatedAt(LocalDateTime.now());

        return FarmAssetResponse.from(assetRepository.save(asset));
    }

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

    public SuggestedROIResponse calculateSuggestedROI(Long cropPlanId, BigDecimal marketPricePerUnit, String marketUnit,
            int totalUnits, BigDecimal unitPrice) {
        log.info("Calculating suggested ROI for crop plan: {}", cropPlanId);
        CropPlanSummary cropPlan = farmingClient.getCropPlanSummary(cropPlanId);
        if (cropPlan == null || cropPlan.getItems() == null) {
            log.error("Crop plan not found or has no items: {}", cropPlanId);
            throw new DomainException("Crop plan not found or has no items defined", 404);
        }

        float totalExpectedYieldKg = (float) cropPlan.getItems().stream()
                .mapToDouble(item -> item.getExpectedYield()
                        * (item.getYieldUnit() == YIELD_UNIT.TON_PER_HECTARE ? 1000 : 1))
                .sum();

        if (totalExpectedYieldKg <= 0) {
            log.error("Zero expected yield for crop plan: {}", cropPlanId);
            throw new DomainException("No expected yield found in this CropPlan", 400);
        }

        BigDecimal totalInvestment = unitPrice.multiply(BigDecimal.valueOf(totalUnits));
        if (totalInvestment.compareTo(BigDecimal.ZERO) == 0) {
            throw new DomainException("Total investment cannot be zero", 400);
        }

        // market inputs safety
        if (marketPricePerUnit == null) {
            marketPricePerUnit = unitPrice.multiply(BigDecimal.valueOf(1.2)); // default 20% margin
            log.warn("Market price missing, using default: {}", marketPricePerUnit);
        }
        if (marketUnit == null)
            marketUnit = "KG";

        // revenue = marketPricePerUnit * (totalExpectedYieldKg in MarketUnits)
        BigDecimal yieldInMarketUnits = BigDecimal.valueOf(totalExpectedYieldKg);
        if ("TONNE".equalsIgnoreCase(marketUnit) || "TON_PER_HECTARE".equalsIgnoreCase(marketUnit)) {
            yieldInMarketUnits = yieldInMarketUnits.divide(BigDecimal.valueOf(1000), 4, RoundingMode.HALF_UP);
        }

        BigDecimal projectedRevenue = marketPricePerUnit.multiply(yieldInMarketUnits);
        BigDecimal profit = projectedRevenue.subtract(totalInvestment);
        BigDecimal suggestedROI = profit.divide(totalInvestment, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        log.info(
                "ROI Calc for CropPlan {}: Yield={} {}, MktPrice={}, Revenue={}, Investment={}, Profit={}, SuggestedROI={}%",
                cropPlanId, yieldInMarketUnits, marketUnit, marketPricePerUnit, projectedRevenue, totalInvestment,
                profit, suggestedROI);

        String primaryCropName = cropPlan.getItems().stream()
                .filter(item -> "PRIMARY".equals(item.getRole()))
                .map(CropPlanSummary.CropPlanItemSummary::getCropName)
                .findFirst()
                .orElse(cropPlan.getItems().isEmpty() ? "Unknown" : cropPlan.getItems().get(0).getCropName());

        return SuggestedROIResponse.builder()
                .cropPlanId(cropPlanId)
                .plotId(cropPlan.getPlotId())
                .primaryCropName(primaryCropName)
                .totalExpectedYieldKg(totalExpectedYieldKg)
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
