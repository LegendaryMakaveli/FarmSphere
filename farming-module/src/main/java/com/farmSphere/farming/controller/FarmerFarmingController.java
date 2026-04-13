package com.farmSphere.farming.controller;

import com.farmSphere.farming.dto.request.AddIntercropRequest;
import com.farmSphere.farming.dto.request.UpdateTaskStatusRequest;
import com.farmSphere.farming.service.CropPlanService;
import com.farmSphere.farming.service.FarmCycleService;
import com.farmSphere.farming.service.TaskService;
import com.farmSphere.infrastructure.response.ApiResponse;
import com.farmSphere.infrastructure.security.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/farmers/farming")
@RequiredArgsConstructor
@PreAuthorize("hasRole('FARMER')")
public class FarmerFarmingController {

    private final CropPlanService cropPlanService;
    private final FarmCycleService farmCycleService;
    private final TaskService taskService;

    @GetMapping("/get/crop-plan/plot/{plotId}")
    public ResponseEntity<ApiResponse<?>> getCropPlanByPlot(@PathVariable Long plotId) {
        return ResponseEntity.ok(ApiResponse.success("Your crop plan", cropPlanService.getCropPlanByPlot(plotId)));
    }

    @PostMapping("/add/plot/{plotId}/intercrop")
    public ResponseEntity<ApiResponse<?>> addIntercrop(@PathVariable Long plotId, @RequestBody @Valid AddIntercropRequest request) {
        Long farmerId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.status(201).body(ApiResponse.success("Intercrop added to your plan", cropPlanService.addIntercrop(plotId, farmerId, request)));
    }

    @GetMapping("/get/my-farm-cycles")
    public ResponseEntity<ApiResponse<?>> getMyFarmCycles() {
        Long farmerId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Your farm cycles", farmCycleService.getMyFarmCycles(farmerId)));
    }

    @GetMapping("/get/my-tasks")
    public ResponseEntity<ApiResponse<?>> getMyTasks() {
        Long farmerId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Your tasks", taskService.getTasksByFarmer(farmerId)));
    }

    @GetMapping("/my-tasks/pending")
    public ResponseEntity<ApiResponse<?>> getMyPendingTasks() {
        Long farmerId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Your pending tasks", taskService.getPendingTasksByFarmer(farmerId)));
    }

    @PatchMapping("/tasks/{taskId}/status")
    public ResponseEntity<ApiResponse<?>> updateTaskStatus(@PathVariable Long taskId, @RequestBody @Valid UpdateTaskStatusRequest request) {
        Long farmerId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Task updated", taskService.updateTaskStatus(taskId, farmerId, request)));
    }
}