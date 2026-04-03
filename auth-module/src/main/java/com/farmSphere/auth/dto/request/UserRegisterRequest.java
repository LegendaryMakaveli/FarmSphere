package com.farmSphere.auth.dto.request;

import com.farmSphere.auth.data.model.GENDER;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterRequest {
    @NotBlank(message = "First name is required!")
    private String firstName;
    @NotBlank(message = "Second name is required!")
    private String secondName;
    @NotBlank(message = "Email is required!")
    private String email;
    @NotBlank(message = "Phone number is required!")
    private String phoneNumber;
    @NotBlank(message = "Password is required!")
    private String password;
    @NotBlank(message = "Address is required!")
    private String address;
    @NotBlank(message = "Gender is required!")
    private GENDER gender;
}
