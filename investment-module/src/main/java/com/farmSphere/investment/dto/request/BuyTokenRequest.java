package com.farmSphere.investment.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BuyTokenRequest {
    @NotNull(message = "Units to buy is required")
    @Min(value = 1, message = "Must buy at least 1 unit")
    private int units;
}
