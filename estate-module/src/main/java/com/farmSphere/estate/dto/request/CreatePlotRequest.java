package com.farmSphere.estate.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CreatePlotRequest {
    @NotBlank(message = "Cluster ID is required")
    private Long clusterId;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Plot size is required")
    private float plotSize;

    @NotBlank(message = "Soil type is required")
    private String soilType;
}
