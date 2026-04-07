package com.farmSphere.marketplace.dto.response;


import com.farmSphere.marketplace.data.model.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderItemResponse {
    private Long orderItemId;
    private Long produceId;
    private String cropName;
    private String farmerName;
    private float quantity;
    private String unit;
    private BigDecimal unitPriceAtOrder;
    private BigDecimal subtotal;

    public static OrderItemResponse from(OrderItem item) {
        return OrderItemResponse.builder()
                .orderItemId(item.getOrderItemId())
                .produceId(item.getProduce().getProduceId())
                .cropName(item.getProduce().getCropName())
                .farmerName(item.getProduce().getFarmerName())
                .quantity(item.getQuantity())
                .unit(item.getProduce().getUnit())
                .unitPriceAtOrder(item.getUnitPriceAtOrder())
                .subtotal(item.getSubtotal())
                .build();
    }
}
