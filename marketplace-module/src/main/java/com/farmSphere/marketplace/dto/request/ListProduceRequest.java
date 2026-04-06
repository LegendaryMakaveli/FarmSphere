package com.farmSphere.marketplace.dto.request;


import com.farmSphere.core.enums.PRODUCE_CATEGORY;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ListProduceRequest {
    @NotBlank(message = "Crop name is required")
    private String cropName;

    @NotBlank(message = "Category is required")
    private PRODUCE_CATEGORY category;

    @NotBlank(message = "Quantity is required")
    @DecimalMin(value = "0.1", message = "Quantity must be greater than 0")
    private float quantityAvailable;

    @NotBlank(message = "Unit is required")
    private String unit;

    @NotBlank(message = "Price per unit is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal pricePerUnit;

    @NotBlank(message = "Harvest date is required")
    private LocalDate harvestDate;

    @NotBlank(message = "Expiry date is required")
    private LocalDate expiryDate;

    private String description;

    private String imageUrl;
}
