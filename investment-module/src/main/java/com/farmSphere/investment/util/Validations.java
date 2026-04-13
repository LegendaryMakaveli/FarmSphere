package com.farmSphere.investment.util;

import com.farmSphere.core.enums.ASSET_STATUS;
import com.farmSphere.core.enums.LISTING_STATUS;
import com.farmSphere.core.enums.YIELD_STATUS;
import com.farmSphere.investment.data.model.FarmAsset;
import com.farmSphere.investment.data.model.Token;
import com.farmSphere.investment.data.model.TokenListing;
import com.farmSphere.investment.data.model.YieldDistribution;
import com.farmSphere.investment.dto.request.BuyTokenRequest;
import com.farmSphere.investment.dto.request.CreateAssetRequest;
import com.farmSphere.investment.dto.request.ListTokenRequest;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Validations {

    @NonNull
    public static FarmAsset getFarmAsset(CreateAssetRequest request) {
        FarmAsset asset = new FarmAsset();
        asset.setCropPlanId(request.getCropPlanId());
        asset.setCropName(request.getCropName());
        asset.setFarmerName(request.getFarmerName());
        asset.setTotalUnits(request.getTotalUnits());
        asset.setUnitPrice(request.getUnitPrice());
        asset.setFundingDeadline(request.getFundingDeadline());
        asset.setStatus(ASSET_STATUS.OPEN);
        asset.setUnitsSold(0);
        asset.setCreatedAt(LocalDateTime.now());
        return asset;
    }

    @NonNull
    public static Token getTokenOrAddToExisting(Long investorId, String investorEmail, String investorName, BuyTokenRequest request, Token token, FarmAsset asset, BigDecimal totalCost) {
        if (token == null) {
            token = new Token();
            token.setFarmAsset(asset);
            token.setOwnerId(investorId);
            token.setOwnerEmail(investorEmail);
            token.setOwnerName(investorName);
            token.setUnitsOwned(request.getUnits());
            token.setPurchasePricePerUnit(asset.getUnitPrice());
            token.setTotalInvested(totalCost);
        } else {
            BigDecimal newTotal = token.getTotalInvested().add(totalCost);
            int newUnits = token.getUnitsOwned() + request.getUnits();
            BigDecimal avgPrice = newTotal.divide(BigDecimal.valueOf(newUnits), 2, java.math.RoundingMode.HALF_UP);
            token.setUnitsOwned(newUnits);
            token.setTotalInvested(newTotal);
            token.setPurchasePricePerUnit(avgPrice);
        }
        return token;
    }

    @NonNull
    public static TokenListing getTokenListing(Long sellerId, String sellerEmail, String sellerName, ListTokenRequest request, Token token) {
        TokenListing listing = new TokenListing();
        listing.setToken(token);
        listing.setSellerId(sellerId);
        listing.setSellerEmail(sellerEmail);
        listing.setSellerName(sellerName);
        listing.setUnitsToSell(request.getUnitsToSell());
        listing.setAskingPricePerUnit(request.getAskingPricePerUnit());
        listing.setStatus(LISTING_STATUS.OPEN);
        listing.setListedAt(LocalDateTime.now());
        return listing;
    }

    @NonNull
    public static Token getBuyerToken(Long buyerId, String buyerEmail, String buyerName, Token buyerToken, FarmAsset asset, TokenListing listing, BigDecimal totalCost) {
        if (buyerToken == null) {
            buyerToken = new Token();
            buyerToken.setFarmAsset(asset);
            buyerToken.setOwnerId(buyerId);
            buyerToken.setOwnerEmail(buyerEmail);
            buyerToken.setOwnerName(buyerName);
            buyerToken.setUnitsOwned(listing.getUnitsToSell());
            buyerToken.setPurchasePricePerUnit(listing.getAskingPricePerUnit());
            buyerToken.setTotalInvested(totalCost);
        } else {
            BigDecimal newTotal = buyerToken.getTotalInvested().add(totalCost);
            int newUnits = buyerToken.getUnitsOwned() + listing.getUnitsToSell();
            BigDecimal avgPrice = newTotal.divide(BigDecimal.valueOf(newUnits), 2, java.math.RoundingMode.HALF_UP);
            buyerToken.setUnitsOwned(newUnits);
            buyerToken.setTotalInvested(newTotal);
            buyerToken.setPurchasePricePerUnit(avgPrice);
        }
        return buyerToken;
    }

    @NonNull
    public static void getTokenRecord(Long buyerId, String buyerEmail, String buyerName, TokenListing listing) {
        listing.setStatus(LISTING_STATUS.SOLD);
        listing.setBuyerId(buyerId);
        listing.setBuyerEmail(buyerEmail);
        listing.setBuyerName(buyerName);
        listing.setSoldAt(LocalDateTime.now());
    }

    @NonNull
    public static YieldDistribution getYieldDistribution(Long adminId, FarmAsset asset, BigDecimal totalPaidOut, int investorsPaid) {
        YieldDistribution distribution = new YieldDistribution();
        distribution.setFarmAsset(asset);
        distribution.setActualROI(asset.getActualROI());
        distribution.setTotalPaidOut(totalPaidOut);
        distribution.setTotalInvestorsPaid(investorsPaid);
        distribution.setStatus(YIELD_STATUS.PAID_OUT);
        distribution.setDistributedByAdminId(adminId);
        distribution.setDistributedAt(LocalDateTime.now());
        return distribution;
    }
}
