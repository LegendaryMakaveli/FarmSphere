package com.farmSphere.estate.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CreateEstateRequest {
    @NotBlank(message = "Estate name is required")
    @Size(min = 2, max = 50, message = "Estate name must be between 2 and 50 characters")
    private String name;

    @NotNull(message = "Total size is required")
    private float totalSize;

    @NotBlank(message = "Location is required")
    private String location;

    private String description;
}
