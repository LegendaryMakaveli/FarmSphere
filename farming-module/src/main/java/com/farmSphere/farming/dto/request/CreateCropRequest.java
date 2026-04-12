package com.farmSphere.farming.dto.request;


import com.farmSphere.core.enums.PRODUCE_CATEGORY;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCropRequest {
    @NotBlank(message = "Crop name is required")
    private String cropName;

    @NotNull(message = "Category is required")
    private PRODUCE_CATEGORY category;

    @NotNull(message = "Growth duration is required")
    private int growthDurationDays;

    private String description;
}
