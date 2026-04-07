package com.farmSphere.marketplace.dto.response;


import com.farmSphere.marketplace.data.model.Order;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
public class OrderResponse {
    private Long orderId;
    private Long buyerId;
    private String buyerName;
    private String buyerEmail;
    private String buyerPhone;
    private BigDecimal totalAmount;
    private String status;
    private String cancellationReason;
    private String orderDate;
    private String updatedAt;
    private List<OrderItemResponse> items;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .buyerId(order.getBuyerId())
                .buyerName(order.getBuyerName())
                .buyerEmail(order.getBuyerEmail())
                .buyerPhone(order.getBuyerPhone())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .cancellationReason(order.getCancellationReason())
                .orderDate(FORMATTER.format(order.getOrderDate()))
                .updatedAt(order.getUpdatedAt() != null ? FORMATTER.format(order.getUpdatedAt()) : null)
                .items(order.getItems() != null ? order.getItems().stream()
                          .map(OrderItemResponse::from)
                          .toList()
                        : List.of())
                .build();
    }
}