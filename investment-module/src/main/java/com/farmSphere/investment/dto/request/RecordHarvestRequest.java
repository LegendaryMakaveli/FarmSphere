package com.farmSphere.investment.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RecordHarvestRequest {
    @NotNull(message = "Actual ROI is required")
    @DecimalMin(value = "0.0", message = "ROI cannot be negative")
    private float actualROI;
}
