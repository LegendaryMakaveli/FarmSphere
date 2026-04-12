package com.farmSphere.farming.dto.request;


import com.farmSphere.core.enums.TASK_STATUS;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateTaskStatusRequest {
    @NotNull(message = "Status is required")
    private TASK_STATUS status;
}
