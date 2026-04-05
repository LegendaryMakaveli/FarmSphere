package com.farmSphere.estate.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CreateEstateRequest {
    @NotBlank(message = "Estate name is required")
    private String name;

    @NotBlank(message = "Total size is required")
    private float totalSize;

    @NotBlank(message = "Location is required")
    private String location;

    private String description;
}
