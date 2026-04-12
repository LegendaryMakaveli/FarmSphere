package com.farmSphere.auth.dto.request;

import com.farmSphere.auth.data.model.GENDER;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class UserRegisterRequest {
    @NotBlank(message = "First name is required!")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Second name is required!")
    @Size(min = 2, max = 50, message = "second name must be between 2 and 50 characters")
    private String secondName;

    @NotBlank(message = "Email is required!")
    @Email(message = "Please enter a valid email address!")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$", message = "Please enter a valid email address!")
    private String email;

    @NotBlank(message = "Phone number is required!")
    @Size(min = 11, max = 12, message = "phone number must be between 11 and 12 characters")
    private String phoneNumber;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, max = 16, message = "password must be at least 6 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,15}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;

    @NotBlank(message = "Address is required!")
    @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
    private String address;

    @NotNull(message = "Gender is required!")
    private GENDER gender;

    @NotNull(message = "Age is required!")
    private int age;
}
