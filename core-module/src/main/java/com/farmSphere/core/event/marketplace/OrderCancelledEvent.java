package com.farmSphere.core.event.marketplace;

public class OrderCancelledEvent {
    private final Long orderId;
    private final Long buyerId;
    private final String buyerEmail;

    public OrderCancelledEvent(Long orderId, Long buyerId, String buyerEmail) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.buyerEmail = buyerEmail;
    }
    public Long getOrderId() { return orderId; }
    public Long getBuyerId() { return buyerId; }
    public String getBuyerEmail() { return buyerEmail; }
}
