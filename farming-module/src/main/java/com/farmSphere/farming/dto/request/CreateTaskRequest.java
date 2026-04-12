package com.farmSphere.farming.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateTaskRequest {
    @NotNull(message = "Farm cycle ID is required")
    private Long farmCycleId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Due date is required")
    private LocalDateTime dueDate;

    @NotNull(message = "Assigned farmer ID is required")
    private Long assignedFarmerId;

    @NotBlank(message = "Assigned farmer email is required")
    private String assignedFarmerEmail;
}
