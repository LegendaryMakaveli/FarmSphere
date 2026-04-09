package com.farmSphere.investment.service;

import com.farmSphere.investment.dto.request.BuyTokenRequest;
import com.farmSphere.investment.dto.request.ListTokenRequest;
import com.farmSphere.investment.dto.response.TokenListingResponse;
import com.farmSphere.investment.dto.response.TokenResponse;

import java.util.List;

public interface TokenService {
    TokenResponse buyFromAsset(Long assetId, Long investorId, String investorEmail, String investorName, BuyTokenRequest request);
    TokenListingResponse listTokenForSale(Long tokenId, Long sellerId, String sellerEmail, String sellerName, ListTokenRequest request);
    TokenResponse buyFromListing(Long listingId, Long buyerId, String buyerEmail, String buyerName);
    void cancelListing(Long listingId, Long sellerId);
    List<TokenResponse> getMyTokens(Long investorId);
    List<TokenListingResponse> getMyListings(Long sellerId);
    List<TokenListingResponse> getOpenListings();
    List<TokenListingResponse> getListingsByAsset(Long assetId);
    List<TokenListingResponse> getAllListings();

}
