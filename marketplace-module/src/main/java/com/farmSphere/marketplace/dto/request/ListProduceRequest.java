package com.farmSphere.marketplace.dto.request;


import com.farmSphere.core.enums.PRODUCE_CATEGORY;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ListProduceRequest {
    @NotBlank(message = "Crop name is required")
    @Size(min = 2, max = 50, message = "Crop name must be between 2 and 50 characters")
    private String cropName;

    @NotBlank(message = "Category is required")
    private PRODUCE_CATEGORY category;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.1", message = "Quantity must be greater than 0")
    private float quantityAvailable;

    @NotBlank(message = "Unit is required")
    private String unit;

    @NotNull(message = "Price per unit is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal pricePerUnit;

    @NotNull(message = "Harvest date is required")
    private LocalDate harvestDate;

    @NotNull(message = "Expiry date is required")
    private LocalDate expiryDate;

    private String description;

    private String imageUrl;
}
