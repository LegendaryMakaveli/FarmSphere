package com.farmSphere.tool.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ToolBookingRequest {
    @NotNull(message = "Tool ID is required")
    private Long toolId;

    @NotBlank(message = "Tool name is required")
    @Size(min = 2, max = 50, message = "Tool name must be between 2 and 50 characters")
    private String toolName;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantityRequested;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
}
