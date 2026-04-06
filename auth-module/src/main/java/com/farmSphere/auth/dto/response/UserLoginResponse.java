package com.farmSphere.auth.dto.response;


import com.farmSphere.auth.data.model.ROLE;
import com.farmSphere.auth.data.model.UserProfileStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponse {
    private String message;
    private String token;
    private ROLE role;
    private String email;
    private Long userId;
    private String firstName;
    private String secondName;
    private String phoneNumber;
    private String lastLogin;
    private UserProfileStatus profileStatus;
}
