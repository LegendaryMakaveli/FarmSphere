package com.farmSphere.marketplace.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CancelOrderRequest {
    @NotBlank(message = "Cancellation reason is required")
    @Size(min = 5, max = 100, message = "Cancellation reason must be between 5 and 100 characters")
    private String reason;
}
