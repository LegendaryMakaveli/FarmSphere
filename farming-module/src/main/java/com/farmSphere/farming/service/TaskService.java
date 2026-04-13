package com.farmSphere.farming.service;

import com.farmSphere.farming.dto.request.CreateTaskRequest;
import com.farmSphere.farming.dto.request.UpdateTaskStatusRequest;
import com.farmSphere.farming.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(CreateTaskRequest request);
    TaskResponse updateTaskStatus(Long taskId, Long farmerId, UpdateTaskStatusRequest request);
    List<TaskResponse> getTasksByFarmer(Long farmerId);
    List<TaskResponse> getTasksByFarmCycle(Long farmCycleId);
    List<TaskResponse> getPendingTasksByFarmer(Long farmerId);

}
