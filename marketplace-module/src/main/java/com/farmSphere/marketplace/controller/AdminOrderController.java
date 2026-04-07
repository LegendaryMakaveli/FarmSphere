package com.farmSphere.marketplace.controller;

import com.farmSphere.core.enums.ORDER_STATUS;
import com.farmSphere.infrastructure.response.ApiResponse;
import com.farmSphere.infrastructure.security.SecurityUtils;
import com.farmSphere.marketplace.dto.request.ConfirmSaleRequest;
import com.farmSphere.marketplace.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAllOrders() {
        return ResponseEntity.ok(ApiResponse.success("All orders", orderService.getAllOrders()));
    }

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<?>> getPendingOrders() {
        return ResponseEntity.ok(ApiResponse.success("Pending orders", orderService.getOrdersByStatus(ORDER_STATUS.PENDING)));
    }

    @GetMapping("/matched")
    public ResponseEntity<ApiResponse<?>> getMatchedOrders() {
        return ResponseEntity.ok(ApiResponse.success("Matched orders", orderService.getOrdersByStatus(ORDER_STATUS.MATCHED)));
    }

    @PatchMapping("/{orderId}/match")
    public ResponseEntity<ApiResponse<?>> matchOrder(@PathVariable Long orderId) {
        Long adminId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Order matched", orderService.matchOrder(orderId, adminId)));
    }

    @PostMapping("/{orderId}/confirm-sale")
    public ResponseEntity<ApiResponse<?>> confirmSale(@PathVariable Long orderId, @RequestBody ConfirmSaleRequest request) {
        Long adminId = SecurityUtils.getCurrentUserId();
        
        return ResponseEntity.ok(ApiResponse.success("Sale confirmed", orderService.confirmSale(orderId, adminId, request)));
    }
}
