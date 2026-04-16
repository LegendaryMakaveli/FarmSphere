package com.farmSphere.farming.dto.request;


import com.farmSphere.core.enums.YIELD_UNIT;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddIntercropRequest {
    @NotNull(message = "Plot ID is required")
    private Long plotId;

    @NotNull(message = "Intercrop Crop ID is required")
    private Long intercropCropId;

    @NotNull(message = "Expected yield is required")
    @DecimalMin(value = "0.1", message = "Expected yield must be greater than 0")
    private float expectedYield;

    @NotNull(message = "Yield unit is required")
    private YIELD_UNIT yieldUnit;

    private String spacingPattern;
}
