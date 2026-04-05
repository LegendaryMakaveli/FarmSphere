package com.farmSphere.estate.controller;

import com.farmSphere.estate.dto.request.AssignPlotRequest;
import com.farmSphere.estate.dto.request.CreateClusterRequest;
import com.farmSphere.estate.dto.request.CreateEstateRequest;
import com.farmSphere.estate.dto.request.CreatePlotRequest;
import com.farmSphere.estate.service.EstateService;
import com.farmSphere.estate.service.PlotService;
import com.farmSphere.infrastructure.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/estate")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminEstateController {

    private final EstateService estateService;
    private final PlotService plotService;

    @PostMapping("/estates")
    public ResponseEntity<ApiResponse<?>> createEstate(@RequestBody CreateEstateRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.success("Estate created", estateService.createEstate(request)));
    }

    @GetMapping("/estates")
    public ResponseEntity<ApiResponse<?>> getAllEstates() {
        return ResponseEntity.ok(
                ApiResponse.success("Estates fetched", estateService.getAllEstates()));
    }

    @PostMapping("/clusters")
    public ResponseEntity<ApiResponse<?>> createCluster(
            @RequestBody CreateClusterRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.success("Cluster created", estateService.createCluster(request)));
    }

    @GetMapping("/estates/{estateId}/clusters")
    public ResponseEntity<ApiResponse<?>> getClustersByEstate(@PathVariable Long estateId) {
        return ResponseEntity.ok(
                ApiResponse.success("Clusters fetched", estateService.getClustersByEstate(estateId)));
    }

    @PostMapping("/plots")
    public ResponseEntity<ApiResponse<?>> createPlot(
            @RequestBody CreatePlotRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.success("Plot created", plotService.createPlot(request)));
    }

    @GetMapping("/plots")
    public ResponseEntity<ApiResponse<?>> getAllPlots() {
        return ResponseEntity.ok(
                ApiResponse.success("Plots fetched", plotService.getAllPlots()));
    }

    @GetMapping("/plots/available")
    public ResponseEntity<ApiResponse<?>> getAvailablePlots() {
        return ResponseEntity.ok(
                ApiResponse.success("Available plots fetched", plotService.getAvailablePlots()));
    }

    @PatchMapping("/plots/{plotId}/assign")
    public ResponseEntity<ApiResponse<?>> assignPlot(
            @PathVariable Long plotId,
            @RequestBody AssignPlotRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Plot assigned successfully", plotService.assignPlot(plotId, request)));
    }

    @PatchMapping("/plots/{plotId}/unassign")
    public ResponseEntity<ApiResponse<?>> unassignPlot(@PathVariable Long plotId) {
        return ResponseEntity.ok(
                ApiResponse.success("Plot unassigned successfully", plotService.unassignPlot(plotId)));
    }
}