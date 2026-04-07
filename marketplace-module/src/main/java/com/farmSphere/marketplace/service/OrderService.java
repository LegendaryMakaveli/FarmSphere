package com.farmSphere.marketplace.service;


import com.farmSphere.core.enums.ORDER_STATUS;
import com.farmSphere.marketplace.dto.request.CancelOrderRequest;
import com.farmSphere.marketplace.dto.request.ConfirmSaleRequest;
import com.farmSphere.marketplace.dto.request.PlaceOrderRequest;
import com.farmSphere.marketplace.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(Long buyerId, String buyerEmail, String buyerName, String buyerPhone, PlaceOrderRequest request);
    OrderResponse cancelOrder(Long orderId, Long buyerId, CancelOrderRequest request);
    OrderResponse matchOrder(Long orderId, Long adminId);
    OrderResponse confirmSale(Long orderId, Long adminId, ConfirmSaleRequest request);
    List<OrderResponse> getMyOrders(Long buyerId);
    OrderResponse getOrderById(Long orderId, Long buyerId);
    List<OrderResponse> getOrdersByStatus(ORDER_STATUS status);
    List<OrderResponse> getAllOrders();
}
