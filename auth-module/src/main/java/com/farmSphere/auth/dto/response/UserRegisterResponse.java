package com.farmSphere.auth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterResponse {
    private String message;
    private String firstName;
    private String secondName;
    private String email;
    private String phoneNumber;
    private String dateOfRegistration;
    private String address;
    private String gender;
}
