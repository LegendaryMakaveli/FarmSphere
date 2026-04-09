package com.farmSphere.estate.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class AssignPlotRequest {
    @NotNull(message = "Farmer ID is required")
    private Long farmerId;

    @NotBlank(message = "Farmer email is required")
    @Email(message = "Invalid email format")
    private String farmerEmail;

    @NotBlank(message = "Farmer name is required")
    @Size(min = 2, max = 50, message = "Farmer name must be between 3 and 50 characters")
    private String farmerName;
}
