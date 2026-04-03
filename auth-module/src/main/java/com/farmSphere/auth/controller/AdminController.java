package com.farmSphere.auth.controller;

import com.farmSphere.auth.service.AdminService;
import com.farmSphere.infrastructure.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminIdentityService;

    @GetMapping("/farmers/pending")
    public ResponseEntity<ApiResponse<?>> getPendingFarmers() {
        return ResponseEntity.ok(
                ApiResponse.success("Pending farmers fetched",
                        adminIdentityService.getPendingFarmers())
        );
    }

    @GetMapping("/investors/pending")
    public ResponseEntity<ApiResponse<?>> getPendingInvestors() {
        return ResponseEntity.ok(
                ApiResponse.success("Pending investors fetched",
                        adminIdentityService.getPendingInvestors())
        );
    }

    @PatchMapping("/farmers/{farmerId}/approve")
    public ResponseEntity<ApiResponse<?>> approveFarmer(@PathVariable Long farmerId) {
        adminIdentityService.approveFarmer(farmerId);
        return ResponseEntity.ok(
                ApiResponse.success("Farmer approved successfully")
        );
    }

    @PatchMapping("/farmers/{farmerId}/reject")
    public ResponseEntity<ApiResponse<?>> rejectFarmer(@PathVariable Long farmerId, @RequestBody String reason) {
        adminIdentityService.rejectFarmer(farmerId, reason);
        return ResponseEntity.ok(
                ApiResponse.success("Farmer rejected")
        );
    }

    @PatchMapping("/investors/{investorId}/approve")
    public ResponseEntity<ApiResponse<?>> approveInvestor(@PathVariable Long investorId) {
        adminIdentityService.approveInvestor(investorId);
        return ResponseEntity.ok(
                ApiResponse.success("Investor approved successfully")
        );
    }

    @PatchMapping("/investors/{investorId}/reject")
    public ResponseEntity<ApiResponse<?>> rejectInvestor(@PathVariable Long investorId, @RequestBody String reason) {
        adminIdentityService.rejectInvestor(investorId, reason);
        return ResponseEntity.ok(
                ApiResponse.success("Investor rejected")
        );
    }
}
