package com.farmSphere.auth.dto.request;

import com.farmSphere.auth.data.model.EXPERIENCE;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UpgradeToFarmerRequest {
    private String farmName;
    private String farmAddress;

    @NotBlank(message = "Experience level is required!")
    @Enumerated(EnumType.STRING)
    private EXPERIENCE experienceLevel;

    private float totalLandSize;
    private Long assignedPlotId;
}
