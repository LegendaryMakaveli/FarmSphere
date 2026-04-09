package com.farmSphere.investment.dto.response;


import com.farmSphere.investment.data.model.FarmAsset;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class FarmAssetResponse {
    private Long id;
    private Long cropPlanId;
    private String cropName;
    private String farmerName;
    private int totalUnits;
    private int unitsSold;
    private int remainingUnits;
    private BigDecimal unitPrice;
    private float expectedROI;
    private float actualROI;
    private String status;
    private String fundingDeadline;
    private String createdAt;
    private BigDecimal projectedReturnPerUnit;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static FarmAssetResponse from(FarmAsset asset) {
        BigDecimal projectedReturn = asset.getUnitPrice()
                .multiply(BigDecimal.valueOf(asset.getExpectedROI()))
                .divide(BigDecimal.valueOf(100))
                .add(asset.getUnitPrice());

        return FarmAssetResponse.builder()
                .id(asset.getId())
                .cropPlanId(asset.getCropPlanId())
                .cropName(asset.getCropName())
                .farmerName(asset.getFarmerName())
                .totalUnits(asset.getTotalUnits())
                .unitsSold(asset.getUnitsSold())
                .remainingUnits(asset.getRemainingUnits())
                .unitPrice(asset.getUnitPrice())
                .expectedROI(asset.getExpectedROI())
                .actualROI(asset.getActualROI())
                .status(asset.getStatus().name())
                .fundingDeadline(DATE_FORMATTER.format(asset.getFundingDeadline()))
                .createdAt(FORMATTER.format(asset.getCreatedAt()))
                .projectedReturnPerUnit(projectedReturn)
                .build();
    }
}












//                Total Return = Initial Price + (Initial Price * Expected ROI / 100)
