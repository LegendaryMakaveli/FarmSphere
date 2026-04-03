package com.farmSphere.auth.service;

import com.farmSphere.auth.data.model.UserProfileStatus;
import com.farmSphere.auth.dto.request.PasswordResetRequest;
import com.farmSphere.auth.dto.request.UpgradeToFarmerRequest;
import com.farmSphere.auth.dto.request.UserLoginRequest;
import com.farmSphere.auth.dto.request.UserRegisterRequest;
import com.farmSphere.auth.dto.response.UserLoginResponse;
import com.farmSphere.auth.dto.response.UserRegisterResponse;

public interface AuthService {
    UserRegisterResponse register(UserRegisterRequest request);
    UserLoginResponse login(UserLoginRequest request);
    UserProfileStatus upgradeToFarmer(Long userId, String email, UpgradeToFarmerRequest request);
    UserProfileStatus upgradeToInvestor(Long userId, String email);
    UserProfileStatus getProfileStatus(Long userId);
    String resetPassword(PasswordResetRequest request);

}
