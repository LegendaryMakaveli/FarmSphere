package com.farmSphere.farming.dto.request;


import com.farmSphere.core.enums.YEILD_UNIT;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCropPlanRequest {
    @NotNull(message = "Plot ID is required")
    private Long plotId;

    @NotNull(message = "Primary crop ID is required")
    private Long primaryCropId;

    @NotNull(message = "Expected yield is required")
    @DecimalMin(value = "0.1", message = "Expected yield must be greater than 0")
    private float expectedYield;

    @NotNull(message = "Yield unit is required")
    private YEILD_UNIT yieldUnit;

    private String spacingPattern;

    private boolean intercroppingEnabled = false;
}
