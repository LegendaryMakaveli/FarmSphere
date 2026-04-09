package com.farmSphere.auth.dto.response;


import com.farmSphere.auth.data.model.UserProfileStatus;
import lombok.Builder;
import lombok.Data;


import java.util.Set;

@Data
@Builder
public class UserLoginResponse {
    private String message;
    private String token;
    private Set<String> roles;
    private String email;
    private Long userId;
    private String firstName;
    private String secondName;
    private String phoneNumber;
    private String lastLogin;
    private UserProfileStatus profileStatus;
}
