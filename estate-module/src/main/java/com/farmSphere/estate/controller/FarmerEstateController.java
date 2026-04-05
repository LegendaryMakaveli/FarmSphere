package com.farmSphere.estate.controller;

import com.farmSphere.estate.service.PlotService;
import com.farmSphere.infrastructure.response.ApiResponse;
import com.farmSphere.infrastructure.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/farmers/estate")
@RequiredArgsConstructor
@PreAuthorize("hasRole('FARMER')")
public class FarmerEstateController {

    private final PlotService plotService;

    @GetMapping("/plot")
    public ResponseEntity<ApiResponse<?>> getMyPlot() {
        Long farmerId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(
                ApiResponse.success("Your plot fetched", plotService.getMyPlot(farmerId)));
    }
}