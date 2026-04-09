package com.farmSphere.investment.controller;

import com.farmSphere.infrastructure.response.ApiResponse;
import com.farmSphere.infrastructure.security.SecurityUtils;
import com.farmSphere.investment.dto.request.CreateAssetRequest;
import com.farmSphere.investment.dto.request.RecordHarvestRequest;
import com.farmSphere.investment.dto.request.UpdateAssetPriceRequest;
import com.farmSphere.investment.service.FarmAssetService;
import com.farmSphere.investment.service.TokenService;
import com.farmSphere.investment.service.TokenServiceImplementation;
import com.farmSphere.investment.service.YieldDistributionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/investment")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminInvestmentController {

    private final FarmAssetService assetService;
    private final YieldDistributionService yieldService;
    private final TokenService tokenService;

    @PostMapping("/create-assets")
    public ResponseEntity<ApiResponse<?>> createAsset(@RequestBody @Valid CreateAssetRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.success("Asset created", assetService.createAsset(request)));
    }

    @GetMapping("/get-all-assets")
    public ResponseEntity<ApiResponse<?>> getAllAssets() {
        return ResponseEntity.ok(ApiResponse.success("All assets", assetService.getAllAssets()));
    }

    @PatchMapping("/update/assets/{assetId}/price")
    public ResponseEntity<ApiResponse<?>> updatePrice(@PathVariable Long assetId, @RequestBody @Valid UpdateAssetPriceRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Price updated", assetService.updatePrice(assetId, request)));
    }

    @PatchMapping("/close/assets/{assetId}/funding")
    public ResponseEntity<ApiResponse<?>> closeFunding(@PathVariable Long assetId) {
        return ResponseEntity.ok(ApiResponse.success("Funding closed", assetService.closeFunding(assetId)));
    }

    @PatchMapping("/assets/{assetId}/harvest")
    public ResponseEntity<ApiResponse<?>> recordHarvest(@PathVariable Long assetId, @RequestBody @Valid RecordHarvestRequest request) {
        Long adminId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Harvest recorded", yieldService.recordHarvest(assetId, request, adminId)));
    }

    @PostMapping("/assets/{assetId}/distribute")
    public ResponseEntity<ApiResponse<?>> distributeROI(@PathVariable Long assetId) {
        Long adminId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("ROI distributed", yieldService.distributeROI(assetId, adminId)));
    }

    @GetMapping("/get/assets/{assetId}/distribution")
    public ResponseEntity<ApiResponse<?>> getDistribution(@PathVariable Long assetId) {
        return ResponseEntity.ok(ApiResponse.success("Distribution details", yieldService.getDistribution(assetId)));
    }

    @GetMapping("get/all/listing")
    public ResponseEntity<ApiResponse<?>> getAllListing(){
        return ResponseEntity.ok(ApiResponse.success("All listing", tokenService.getAllListings()));
    }
}
