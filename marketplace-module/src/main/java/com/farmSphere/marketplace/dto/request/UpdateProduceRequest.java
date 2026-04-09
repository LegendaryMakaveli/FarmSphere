package com.farmSphere.marketplace.dto.request;


import com.farmSphere.core.enums.PRODUCE_STATUS;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProduceRequest {
    private Float quantityAvailable;
    @DecimalMin(value = "0.1", message = "Price per unit must be greater than 0")
    private BigDecimal pricePerUnit;
    private String description;
    private String imageUrl;
    private PRODUCE_STATUS status;
}
