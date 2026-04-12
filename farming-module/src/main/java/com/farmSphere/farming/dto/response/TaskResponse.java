package com.farmSphere.farming.dto.response;


import com.farmSphere.farming.data.model.Task;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class TaskResponse {
    private Long taskId;
    private Long farmCycleId;
    private String title;
    private String description;
    private String dueDate;
    private Long assignedFarmerId;
    private String assignedFarmerEmail;
    private String status;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static TaskResponse from(Task task) {
        return TaskResponse.builder()
                .taskId(task.getTaskId())
                .farmCycleId(task.getFarmCycleId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(FORMATTER.format(task.getDueDate()))
                .assignedFarmerId(task.getAssignedFarmerId())
                .assignedFarmerEmail(task.getAssignedFarmerEmail())
                .status(task.getStatus().name())
                .build();
    }
}
