package com.farmSphere.tool.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CreateToolRequest {
    @NotBlank(message = "Tool name is required")
    private String toolName;

    private String description;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantityAvailable;

    @NotBlank(message = "Condition status is required")
    private String conditionStatus;
}
