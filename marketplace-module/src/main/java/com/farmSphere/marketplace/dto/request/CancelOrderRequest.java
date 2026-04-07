package com.farmSphere.marketplace.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CancelOrderRequest {
    @NotBlank(message = "Cancellation reason is required")
    private String reason;
}
