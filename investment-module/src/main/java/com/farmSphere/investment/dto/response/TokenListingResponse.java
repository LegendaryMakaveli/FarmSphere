package com.farmSphere.investment.dto.response;


import com.farmSphere.investment.data.model.TokenListing;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TokenListingResponse {
    private Long listingId;
    private Long assetId;
    private String cropName;
    private Long sellerId;
    private String sellerName;
    private int unitsToSell;
    private BigDecimal askingPricePerUnit;
    private BigDecimal totalAskingPrice;
    private String status;
    private String listedAt;

    public static TokenListingResponse from(TokenListing listing) {
        return TokenListingResponse.builder()
                .listingId(listing.getListingId())
                .assetId(listing.getToken().getFarmAsset().getId())
                .cropName(listing.getToken().getFarmAsset().getCropName())
                .sellerId(listing.getSellerId())
                .sellerName(listing.getSellerName())
                .unitsToSell(listing.getUnitsToSell())
                .askingPricePerUnit(listing.getAskingPricePerUnit())
                .totalAskingPrice(listing.getAskingPricePerUnit().multiply(BigDecimal.valueOf(listing.getUnitsToSell())))
                .status(listing.getStatus().name())
                .listedAt(listing.getListedAt().toString())
                .build();
    }
}
