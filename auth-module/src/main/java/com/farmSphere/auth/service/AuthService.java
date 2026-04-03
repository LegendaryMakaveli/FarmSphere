package com.farmSphere.auth.service;

import com.farmSphere.auth.dto.request.PasswordResetRequest;
import com.farmSphere.auth.dto.request.UserLoginRequest;
import com.farmSphere.auth.dto.request.UserRegisterRequest;
import com.farmSphere.auth.dto.response.UserLoginResponse;
import com.farmSphere.auth.dto.response.UserRegisterResponse;

public interface AuthService {
    UserRegisterResponse registerFarmer(UserRegisterRequest request);
    UserLoginResponse loginFarmer(UserLoginRequest request);
    String resetPassword(PasswordResetRequest request);
}
