package com.farmSphere.investment.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ListTokenRequest {
    @NotNull(message = "Units to sell is required")
    @Min(value = 1, message = "Must sell at least 1 unit")
    private int unitsToSell;

    @NotNull(message = "Asking price is required")
    @DecimalMin(value = "1.00", message = "Price must be at least 1")
    private BigDecimal askingPricePerUnit;
}
