package com.farmSphere.farming.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StartFarmCycleRequest {
    @NotNull(message = "Plot ID is required")
    private Long plotId;

    @NotNull(message = "Farmer ID is required")
    private Long farmerId;

    @NotBlank(message = "Farmer email is required")
    private String farmerEmail;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;
}
