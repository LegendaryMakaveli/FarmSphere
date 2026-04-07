package com.farmSphere.marketplace.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class OrderItemRequest {
    @NotNull(message = "Produce ID is required")
    private Long produceId;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.1", message = "Quantity must be greater than 0")
    private float quantity;
}
