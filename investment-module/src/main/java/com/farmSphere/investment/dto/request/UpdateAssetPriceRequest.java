package com.farmSphere.investment.dto.request;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateAssetPriceRequest {
    @NotNull(message = "New unit price is required")
    @DecimalMin(value = "1.00", message = "Price must be at least 1")
    private BigDecimal unitPrice;
}
