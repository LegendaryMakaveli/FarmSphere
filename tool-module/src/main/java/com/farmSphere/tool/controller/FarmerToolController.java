package com.farmSphere.tool.controller;

import com.farmSphere.infrastructure.response.ApiResponse;
import com.farmSphere.infrastructure.security.SecurityUtils;
import com.farmSphere.tool.dto.request.ToolBookingRequest;
import com.farmSphere.tool.service.ToolBookingService;
import com.farmSphere.tool.service.ToolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/farmers/tools")
@RequiredArgsConstructor
@PreAuthorize("hasRole('FARMER')")
public class FarmerToolController {

    private final ToolService toolService;
    private final ToolBookingService bookingService;

    @GetMapping("/get/availableTools")
    public ResponseEntity<ApiResponse<?>> getAvailableTools() {
        return ResponseEntity.ok(
                ApiResponse.success("Available tools", toolService.getAvailableTools()));
    }


    @PostMapping("/bookings")
    public ResponseEntity<ApiResponse<?>> bookTool(@RequestBody @Valid ToolBookingRequest request) {
        Long farmerId = SecurityUtils.getCurrentUserId();
        String farmerEmail = SecurityUtils.getCurrentUserEmail();
        String farmerFirstName = SecurityUtils.getCurrentUserFirstName();
        String farmerLastName = SecurityUtils.getCurrentUserLastName();
        String farmerPhone = SecurityUtils.getCurrentUserPhone();

        return ResponseEntity.status(201).body(ApiResponse.success("Booking submitted", bookingService.createBooking(farmerId, farmerEmail, farmerFirstName,farmerLastName, farmerPhone, request)));
    }


    @GetMapping("/view/bookings")
    public ResponseEntity<ApiResponse<?>> getMyBookings() {
        Long farmerId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Your bookings", bookingService.getMyBookings(farmerId)));
    }
}