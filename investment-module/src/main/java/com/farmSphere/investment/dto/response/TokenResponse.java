package com.farmSphere.investment.dto.response;


import com.farmSphere.investment.data.model.Token;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TokenResponse {
    private Long tokenId;
    private Long assetId;
    private String cropName;
    private String farmerName;
    private int unitsOwned;
    private BigDecimal purchasePricePerUnit;
    private BigDecimal totalInvested;
    private BigDecimal projectedReturn;
    private boolean roiDistributed;
    private String purchaseDate;

    public static TokenResponse from(Token token) {
        BigDecimal projectedReturn = token.getTotalInvested()
                .multiply(BigDecimal.valueOf(token.getFarmAsset().getExpectedROI()))
                .divide(BigDecimal.valueOf(100))
                .add(token.getTotalInvested());

        return TokenResponse.builder()
                .tokenId(token.getTokenId())
                .assetId(token.getFarmAsset().getId())
                .cropName(token.getFarmAsset().getCropName())
                .farmerName(token.getFarmAsset().getFarmerName())
                .unitsOwned(token.getUnitsOwned())
                .purchasePricePerUnit(token.getPurchasePricePerUnit())
                .totalInvested(token.getTotalInvested())
                .projectedReturn(projectedReturn)
                .roiDistributed(token.isRoiDistributed())
                .purchaseDate(token.getPurchaseDate().toString())
                .build();
    }











//                Projected Return = Initial Investment + (Initial Investment * ROI Percentage / 100)
}
