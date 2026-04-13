package com.farmSphere.farming.service;


import com.farmSphere.core.enums.TASK_STATUS;
import com.farmSphere.farming.data.model.Task;
import com.farmSphere.farming.data.repository.FarmCycleRepository;
import com.farmSphere.farming.data.repository.TaskRepository;
import com.farmSphere.farming.dto.request.CreateTaskRequest;
import com.farmSphere.farming.dto.request.UpdateTaskStatusRequest;
import com.farmSphere.farming.dto.response.TaskResponse;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.infrastructure.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.farmSphere.farming.util.Mapper.getTask;

@Service
@RequiredArgsConstructor
public class TaskServiceImplementation implements TaskService{

    private final TaskRepository taskRepository;
    private final FarmCycleRepository farmCycleRepository;


    @Override
    public TaskResponse createTask(CreateTaskRequest request) {
        SecurityUtils.requireAdmin();
        farmCycleRepository.findById(request.getFarmCycleId()).orElseThrow(() -> new DomainException("FarmCycle not found", 404));

        Task task = getTask(request);

        return TaskResponse.from(taskRepository.save(task));
    }


    @Override
    public TaskResponse updateTaskStatus(Long taskId, Long farmerId, UpdateTaskStatusRequest request) {
        SecurityUtils.requireFarmer();
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new DomainException("Task not found", 404));

        if (!task.getAssignedFarmerId().equals(farmerId)) throw new DomainException("This task is not assigned to you", 403);

        task.setStatus(request.getStatus());
        return TaskResponse.from(taskRepository.save(task));
    }

    @Override
    public List<TaskResponse> getTasksByFarmer(Long farmerId) {
        return taskRepository.findAllByAssignedFarmerId(farmerId).stream().map(TaskResponse::from).toList();
    }

    @Override
    public List<TaskResponse> getTasksByFarmCycle(Long farmCycleId) {
        return taskRepository.findAllByFarmCycleId(farmCycleId).stream().map(TaskResponse::from).toList();
    }

    @Override
    public List<TaskResponse> getPendingTasksByFarmer(Long farmerId) {
        return taskRepository.findAllByAssignedFarmerIdAndStatus(farmerId, TASK_STATUS.PENDING).stream().map(TaskResponse::from).toList();
    }
}
