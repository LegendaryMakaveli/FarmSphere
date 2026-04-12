package com.farmSphere.auth.dto.request;

import com.farmSphere.auth.data.model.EXPERIENCE;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class UpgradeToFarmerRequest {
    @NotNull(message = "Experience level is required!")
    private EXPERIENCE experienceLevel;
}
