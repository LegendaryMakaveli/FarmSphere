package com.farmSphere.investment.controller;

import com.farmSphere.infrastructure.response.ApiResponse;
import com.farmSphere.infrastructure.security.SecurityUtils;
import com.farmSphere.investment.dto.request.BuyTokenRequest;
import com.farmSphere.investment.dto.request.ListTokenRequest;
import com.farmSphere.investment.service.FarmAssetService;
import com.farmSphere.investment.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/investor")
@PreAuthorize("hasRole('INVESTOR')")
@RequiredArgsConstructor
public class InvestorController {

    private final FarmAssetService assetService;
    private final TokenService tokenService;

    @GetMapping("/open-assets")
    public ResponseEntity<ApiResponse<?>> getOpenAssets() {
        return ResponseEntity.ok(ApiResponse.success("Open assets", assetService.getOpenAssets()));
    }

    @GetMapping("/assets/{assetId}")
    public ResponseEntity<ApiResponse<?>> getAsset(@PathVariable Long assetId) {
        return ResponseEntity.ok(ApiResponse.success("Asset detail", assetService.getAssetById(assetId)));
    }

    @PostMapping("/assets/{assetId}/invest")
    public ResponseEntity<ApiResponse<?>> buyFromAsset(@PathVariable Long assetId, @RequestBody @Valid BuyTokenRequest request) {
        Long investorId = SecurityUtils.getCurrentUserId();
        String email = SecurityUtils.getCurrentUserEmail();
        String name = SecurityUtils.getCurrentUserFirstName() + " " + SecurityUtils.getCurrentUserLastName();

        return ResponseEntity.status(201).body(ApiResponse.success("Investment successful", tokenService.buyFromAsset(assetId, investorId, email, name, request)));
    }

    @GetMapping("/portfolio")
    public ResponseEntity<ApiResponse<?>> getMyPortfolio() {
        Long investorId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Your portfolio", tokenService.getMyTokens(investorId)));
    }

//    @GetMapping("/portfolio/transactions")
//    public ResponseEntity<ApiResponse<?>> getMyTransactions() {
//        Long investorId = SecurityUtils.getCurrentUserId();
//        return ResponseEntity.ok(ApiResponse.success("Your transactions", tokenService.(investorId)));
//    }

    @PostMapping("/tokens/{tokenId}/listing")
    public ResponseEntity<ApiResponse<?>> listTokenForSale(@PathVariable Long tokenId, @RequestBody @Valid ListTokenRequest request) {
        Long sellerId  = SecurityUtils.getCurrentUserId();
        String email   = SecurityUtils.getCurrentUserEmail();
        String name    = SecurityUtils.getCurrentUserFirstName() + " " + SecurityUtils.getCurrentUserLastName();

        return ResponseEntity.status(201).body(ApiResponse.success("Token listed for sale", tokenService.listTokenForSale(tokenId, sellerId, email, name, request)));
    }

    @GetMapping("/sec/market")
    public ResponseEntity<ApiResponse<?>> getOpenListings() {
        return ResponseEntity.ok(ApiResponse.success("Open token listings", tokenService.getOpenListings()));
    }

    @GetMapping("/assets/{assetId}/market")
    public ResponseEntity<ApiResponse<?>> getListingsByAsset(@PathVariable Long assetId) {
        return ResponseEntity.ok(ApiResponse.success("Listings for asset", tokenService.getListingsByAsset(assetId)));
    }
    @PostMapping("/sec/market/{listingId}/buy")
    public ResponseEntity<ApiResponse<?>> buyFromListing(@PathVariable Long listingId) {
        Long buyerId = SecurityUtils.getCurrentUserId();
        String email = SecurityUtils.getCurrentUserEmail();
        String name  = SecurityUtils.getCurrentUserFirstName() + " " + SecurityUtils.getCurrentUserLastName();

        return ResponseEntity.ok(ApiResponse.success("Token purchased successfully", tokenService.buyFromListing(listingId, buyerId, email, name)));
    }

    @DeleteMapping("/sec/market/{listingId}")
    public ResponseEntity<ApiResponse<?>> cancelListing(@PathVariable Long listingId) {
        Long sellerId = SecurityUtils.getCurrentUserId();
        tokenService.cancelListing(listingId, sellerId);
        return ResponseEntity.ok(ApiResponse.success("Listing cancelled"));
    }

    @GetMapping("/portfolio/listings")
    public ResponseEntity<ApiResponse<?>> getMyListings() {
        Long sellerId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Your listings", tokenService.getMyListings(sellerId)));
    }
}