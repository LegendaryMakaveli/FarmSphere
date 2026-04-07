package com.farmSphere.core.event.marketplace;

import java.math.BigDecimal;

public class SaleConfirmedEvent {
    private final Long orderId;
    private final Long buyerId;
    private final String buyerEmail;
    private final String buyerName;
    private final BigDecimal totalAmount;

    public SaleConfirmedEvent(Long orderId, Long buyerId, String buyerEmail, String buyerName, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.buyerEmail = buyerEmail;
        this.buyerName = buyerName;
        this.totalAmount = totalAmount;
    }
    public Long getOrderId() { return orderId; }
    public Long getBuyerId() { return buyerId; }
    public String getBuyerEmail() { return buyerEmail; }
    public String getBuyerName() { return buyerName; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}
