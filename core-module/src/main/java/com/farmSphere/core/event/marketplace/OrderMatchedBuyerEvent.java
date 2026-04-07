package com.farmSphere.core.event.marketplace;

public class OrderMatchedBuyerEvent {
    private final Long orderId;
    private final String buyerEmail;
    private final String buyerName;

    public OrderMatchedBuyerEvent(Long orderId, String buyerEmail, String buyerName) {
        this.orderId = orderId;
        this.buyerEmail = buyerEmail;
        this.buyerName = buyerName;
    }

    public Long getOrderId() { return orderId; }
    public String getBuyerEmail() { return buyerEmail; }
    public String getBuyerName() { return buyerName; }
}
