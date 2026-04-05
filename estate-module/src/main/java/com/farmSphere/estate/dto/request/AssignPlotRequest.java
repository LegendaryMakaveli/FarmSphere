package com.farmSphere.estate.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class AssignPlotRequest {
    @NotBlank(message = "Farmer ID is required")
    private Long farmerId;

    @NotBlank(message = "Farmer email is required")
    private String farmerEmail;

    @NotBlank(message = "Farmer name is required")
    private String farmerName;
}
