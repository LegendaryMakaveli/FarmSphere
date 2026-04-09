package com.farmSphere.tool.controller;

import com.farmSphere.core.enums.BOOKING_STATUS;
import com.farmSphere.infrastructure.response.ApiResponse;
import com.farmSphere.infrastructure.security.SecurityUtils;
import com.farmSphere.tool.dto.request.AddStockRequest;
import com.farmSphere.tool.dto.request.CreateToolRequest;
import com.farmSphere.tool.dto.request.RejectBookingRequest;
import com.farmSphere.tool.dto.request.UpdateToolRequest;
import com.farmSphere.tool.service.ToolBookingService;
import com.farmSphere.tool.service.ToolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/tools")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminToolController {

    private final ToolService toolService;
    private final ToolBookingService bookingService;

    @PostMapping("/create-tool")
    public ResponseEntity<ApiResponse<?>> createTool(
            @RequestBody CreateToolRequest request) {
        return ResponseEntity.status(201).body(ApiResponse.success("Tool created", toolService.createTool(request)));
    }

    @GetMapping("/get-all-tools")
    public ResponseEntity<ApiResponse<?>> getAllTools() {
        return ResponseEntity.ok(
                ApiResponse.success("All tools", toolService.getAllTools()));
    }

    @GetMapping("/get-all-bookings")
    public ResponseEntity<ApiResponse<?>> getAllBookings() {
        return ResponseEntity.ok(ApiResponse.success("All bookings", bookingService.getAllBookings()));
    }

    @GetMapping("/bookings/pending")
    public ResponseEntity<ApiResponse<?>> getPendingBookings() {
        return ResponseEntity.ok(ApiResponse.success("Pending bookings", bookingService.getBookingsByStatus(BOOKING_STATUS.PENDING)));
    }

    @PatchMapping("/bookings/{bookingId}/approve")
    public ResponseEntity<ApiResponse<?>> approveBooking(@PathVariable Long bookingId) {
        Long adminId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Booking approved", bookingService.approveBooking(bookingId, adminId)));
    }

    @PatchMapping("/bookings/{bookingId}/reject")
    public ResponseEntity<ApiResponse<?>> rejectBooking(@PathVariable Long bookingId, @RequestBody @Valid RejectBookingRequest request) {
        Long adminId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Booking rejected", bookingService.rejectBooking(bookingId, adminId, request.getReason())));
    }

    @PatchMapping("/bookings/{bookingId}/pickup")
    public ResponseEntity<ApiResponse<?>> pickupTool(@PathVariable Long bookingId) {
        return ResponseEntity.ok(ApiResponse.success("Tool picked up", bookingService.pickupTool(bookingId)));
    }

    @PatchMapping("/bookings/{bookingId}/return")
    public ResponseEntity<ApiResponse<?>> returnTool(@PathVariable Long bookingId) {
        return ResponseEntity.ok(ApiResponse.success("Tool returned", bookingService.returnTool(bookingId)));
    }

    @GetMapping("/get-tool/{toolId}")
    public ResponseEntity<ApiResponse<?>> getToolById(@PathVariable Long toolId) {
        return ResponseEntity.ok(ApiResponse.success("Tool fetched", toolService.getToolById(toolId)));
    }

    @PatchMapping("/update-tool/{toolId}")
    public ResponseEntity<ApiResponse<?>> updateTool(@PathVariable Long toolId, @RequestBody @Valid UpdateToolRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Tool updated", toolService.updateTool(toolId, request)));
    }

    @PatchMapping("/add/{toolId}/stock")
    public ResponseEntity<ApiResponse<?>> addStock(@PathVariable Long toolId, @RequestBody @Valid AddStockRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Stock updated", toolService.addStock(toolId, request)));
    }
}