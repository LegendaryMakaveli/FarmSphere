package com.farmSphere.marketplace.controller;

import com.farmSphere.infrastructure.response.ApiResponse;
import com.farmSphere.infrastructure.security.SecurityUtils;
import com.farmSphere.marketplace.dto.request.CancelOrderRequest;
import com.farmSphere.marketplace.dto.request.PlaceOrderRequest;
import com.farmSphere.marketplace.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('BUYER') or hasRole('FARMER') or hasRole('INVESTOR')")
public class BuyerOrderController {

    private final OrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<ApiResponse<?>> placeOrder(@RequestBody @Valid PlaceOrderRequest request) {

        Long buyerId = SecurityUtils.getCurrentUserId();
        String buyerEmail = SecurityUtils.getCurrentUserEmail();
        String buyerName  = SecurityUtils.getCurrentUserFirstName()+ " " + SecurityUtils.getCurrentUserLastName();
        String buyerPhone = SecurityUtils.getCurrentUserPhone();

        return ResponseEntity.status(201).body(ApiResponse.success("Order placed successfully", orderService.placeOrder(buyerId, buyerEmail, buyerName, buyerPhone, request)));
    }

    @GetMapping("/get-order")
    public ResponseEntity<ApiResponse<?>> getMyOrders() {
        Long buyerId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(ApiResponse.success("Your orders", orderService.getMyOrders(buyerId)));
    }

    @GetMapping("/get-single-order/{orderId}")
    public ResponseEntity<ApiResponse<?>> getOrderById(@PathVariable Long orderId) {
        Long buyerId = SecurityUtils.getCurrentUserId();

        return ResponseEntity.ok(ApiResponse.success("Order detail", orderService.getOrderById(orderId, buyerId)));
    }

    @PatchMapping("/cancle-order{orderId}")
    public ResponseEntity<ApiResponse<?>> cancelOrder(@PathVariable Long orderId, @RequestBody @Valid CancelOrderRequest request) {
        Long buyerId = SecurityUtils.getCurrentUserId();

        return ResponseEntity.ok(ApiResponse.success("Order cancelled", orderService.cancelOrder(orderId, buyerId, request)));
    }
}