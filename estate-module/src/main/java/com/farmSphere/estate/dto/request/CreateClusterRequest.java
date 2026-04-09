package com.farmSphere.estate.dto.request;

import com.farmSphere.core.enums.FARMING_MODEL;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateClusterRequest {
    @NotNull(message = "Estate ID is required")
    private Long estateId;

    @NotBlank(message = "Cluster name is required")
    private String clusterName;

    @NotNull(message = "Primary crop ID is required")
    private Long primaryCropId;

    @NotBlank(message = "Primary crop name is required")
    private String primaryCropName;

    @NotBlank(message = "Farming model is required")
    private FARMING_MODEL farmingModel;

    private String description;
}
