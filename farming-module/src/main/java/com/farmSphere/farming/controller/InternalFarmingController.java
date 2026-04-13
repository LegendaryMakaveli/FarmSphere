package com.farmSphere.farming.controller;

import com.farmSphere.core.dto.CropPlanSummary;
import com.farmSphere.farming.service.CropPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/farming")
@RequiredArgsConstructor
public class InternalFarmingController {

    private final CropPlanService cropPlanService;

    @GetMapping("/crop-plans/{cropPlanId}/summary")
    public ResponseEntity<CropPlanSummary> getCropPlanSummary(@PathVariable Long cropPlanId) {
        return ResponseEntity.ok(cropPlanService.getCropPlanSummary(cropPlanId));
    }
}