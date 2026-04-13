package com.farmSphere.farming.controller;

import com.farmSphere.core.enums.PRODUCE_CATEGORY;
import com.farmSphere.farming.dto.request.*;
import com.farmSphere.farming.service.*;
import com.farmSphere.infrastructure.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/farming")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminFarmingController {

    private final CropService cropService;
    private final CropPlanService cropPlanService;
    private final FarmCycleService farmCycleService;
    private final TaskService taskService;


    @PostMapping("/create/crops")
    public ResponseEntity<ApiResponse<?>> createCrop(@RequestBody @Valid CreateCropRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.success("Crop created", cropService.createCrop(request)));
    }

    @GetMapping("/get-all/crops")
    public ResponseEntity<ApiResponse<?>> getAllCrops() {
        return ResponseEntity.ok(ApiResponse.success("All crops", cropService.getAllCrops()));
    }

    @GetMapping("/get/crop/{cropId}")
    public ResponseEntity<ApiResponse<?>> getCropById(@PathVariable Long cropId) {
        return ResponseEntity.ok(ApiResponse.success("Crop", cropService.getCropById(cropId)));
    }

    @GetMapping("/get/crop/category/{category}")
    public ResponseEntity<ApiResponse<?>> getCropsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(ApiResponse.success("Crops by category", cropService.getCropsByCategory(PRODUCE_CATEGORY.valueOf(category.toUpperCase()))));
    }


    @PostMapping("/create/crop-plans")
    public ResponseEntity<ApiResponse<?>> createCropPlan(@RequestBody @Valid CreateCropPlanRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.success("Crop plan created", cropPlanService.createCropPlan(request)));
    }

    @GetMapping("/get/crop-plan/{cropPlanId}")
    public ResponseEntity<ApiResponse<?>> getCropPlanById(@PathVariable Long cropPlanId) {
        return ResponseEntity.ok(
                ApiResponse.success("Crop plan", cropPlanService.getCropPlanById(cropPlanId)));
    }

    @GetMapping("/get/crop-plans/plot/{plotId}")
    public ResponseEntity<ApiResponse<?>> getCropPlanByPlot(@PathVariable Long plotId) {
        return ResponseEntity.ok(ApiResponse.success("Crop plan for plot", cropPlanService.getCropPlanByPlot(plotId)));
    }

    @PostMapping("/start/farm-cycles")
    public ResponseEntity<ApiResponse<?>> startFarmCycle(@RequestBody @Valid StartFarmCycleRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.success("Farm cycle started", farmCycleService.startFarmCycle(request)));
    }

    @GetMapping("/get/farm-cycles")
    public ResponseEntity<ApiResponse<?>> getAllFarmCycles() {
        return ResponseEntity.ok(ApiResponse.success("All farm cycles", farmCycleService.getAllFarmCycles()));
    }

    @GetMapping("/farm-cycles/plot/{plotId}/active")
    public ResponseEntity<ApiResponse<?>> getActiveCycleByPlot(@PathVariable Long plotId) {
        return ResponseEntity.ok(ApiResponse.success("Active farm cycle", farmCycleService.getActiveCycleByPlot(plotId)));
    }

    @PatchMapping("/farm-cycles/{farmCycleId}/activate")
    public ResponseEntity<ApiResponse<?>> activateFarmCycle(@PathVariable Long farmCycleId) {
        return ResponseEntity.ok(ApiResponse.success("Farm cycle activated", farmCycleService.activateFarmCycle(farmCycleId)));
    }

    @PatchMapping("/farm-cycles/{farmCycleId}/harvest")
    public ResponseEntity<ApiResponse<?>> recordHarvest(@PathVariable Long farmCycleId, @RequestBody @Valid RecordHarvestRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Harvest recorded — ROI calculation triggered", farmCycleService.recordHarvest(farmCycleId, request)));
    }

    @PostMapping("/create/tasks")
    public ResponseEntity<ApiResponse<?>> createTask(@RequestBody @Valid CreateTaskRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.success("Task created", taskService.createTask(request)));
    }

    @GetMapping("/get/tasks/farm-cycle/{farmCycleId}")
    public ResponseEntity<ApiResponse<?>> getTasksByFarmCycle(@PathVariable Long farmCycleId) {
        return ResponseEntity.ok(ApiResponse.success("Tasks for farm cycle", taskService.getTasksByFarmCycle(farmCycleId)));
    }
}