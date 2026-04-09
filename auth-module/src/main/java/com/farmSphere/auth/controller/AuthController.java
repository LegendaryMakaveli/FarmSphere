package com.farmSphere.auth.controller;

import com.farmSphere.auth.data.model.UserProfileStatus;
import com.farmSphere.auth.dto.request.PasswordResetRequest;
import com.farmSphere.auth.dto.request.UpgradeToFarmerRequest;
import com.farmSphere.auth.dto.request.UserLoginRequest;
import com.farmSphere.auth.dto.request.UserRegisterRequest;
import com.farmSphere.auth.dto.response.UserLoginResponse;
import com.farmSphere.auth.dto.response.UserRegisterResponse;
import com.farmSphere.auth.service.AuthService;
import com.farmSphere.infrastructure.response.ApiResponse;
import com.farmSphere.infrastructure.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserRegisterResponse>> register(@RequestBody @Valid UserRegisterRequest request) {
        UserRegisterResponse response = authService.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponse>> login(@RequestBody @Valid UserLoginRequest request) {
        UserLoginResponse response = authService.login(request);

        return ResponseEntity
                .ok(ApiResponse.success("Login successful", response));
    }


    @PostMapping("/upgrade/farmer")
    public ResponseEntity<ApiResponse<UserProfileStatus>> upgradeToFarmer(@RequestBody @Valid UpgradeToFarmerRequest request) {
        Long userId = SecurityUtils.getCurrentUserId();
        String email = SecurityUtils.getCurrentUserEmail();

        UserProfileStatus status = authService.upgradeToFarmer(userId, email, request);

        return ResponseEntity
                .ok(ApiResponse.success("Account upgraded to Farmer", status));
    }

    @PostMapping("/upgrade/investor")
    public ResponseEntity<ApiResponse<UserProfileStatus>> upgradeToInvestor() {

        Long userId = SecurityUtils.getCurrentUserId();
        String email = SecurityUtils.getCurrentUserEmail();

        UserProfileStatus status = authService.upgradeToInvestor(userId, email);

        return ResponseEntity
                .ok(ApiResponse.success("Account upgraded to Investor", status));
    }


    @GetMapping("/profile/status")
    public ResponseEntity<ApiResponse<UserProfileStatus>> getProfileStatus() {

        Long userId = SecurityUtils.getCurrentUserId();
        UserProfileStatus status = authService.getProfileStatus(userId);

        return ResponseEntity
                .ok(ApiResponse.success("Profile status fetched", status));
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@RequestBody @Valid PasswordResetRequest request) {
        authService.resetPassword(request);

        return ResponseEntity
                .ok(ApiResponse.success("Password reset successful"));
    }
}