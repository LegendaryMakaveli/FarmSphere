package com.farmSphere.investment.dto.request;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateAssetRequest {
    @NotNull(message = "crop id is required")
    private Long cropPlanId;

    @NotBlank(message = "Crop name is required")
    @Size(min = 2, max = 50, message = "Crop name must be between 2 and 50")
    private String cropName;

    @NotBlank(message = "Farmer name is required")
    @Size(min = 2, max = 50, message = "Farmer name must be between 2 and 50")
    private String farmerName;

    @NotNull(message = "total unit must be greater than 1")
    @Min(value = 1, message = "Must have at least 1 unit")
    private int totalUnits;

    @NotNull(message = "unit price must be greater than 0")
    @DecimalMin(value = "1.00", message = "Unit price must be at least 1")
    private BigDecimal unitPrice;

    @NotNull(message = "ROI must be greater than 0")
    @DecimalMin(value = "0.1", message = "ROI must be greater than 0")
    private float expectedROI;

    @NotNull(message = "funding deadline is required")
    private LocalDate fundingDeadline;
}
