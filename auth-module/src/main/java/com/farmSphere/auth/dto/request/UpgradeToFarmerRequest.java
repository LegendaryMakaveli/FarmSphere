package com.farmSphere.auth.dto.request;

import com.farmSphere.auth.data.model.EXPERIENCE;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UpgradeToFarmerRequest {
    @Size(min = 2, max = 50, message = "Farm name must be between 2 and 50 characters")
    private String farmName;
    @Size(min = 5, max = 200, message = "Farm address must be between 5 and 200 characters")
    private String farmAddress;

    @NotBlank(message = "Experience level is required!")
    @Enumerated(EnumType.STRING)
    private EXPERIENCE experienceLevel;

    private float totalLandSize;
    private Long assignedPlotId;
}
