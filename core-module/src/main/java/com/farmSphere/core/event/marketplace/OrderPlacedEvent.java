package com.farmSphere.core.event.marketplace;


import java.math.BigDecimal;


public class OrderPlacedEvent {
    private final Long orderId;
    private final Long buyerId;
    private final String buyerEmail;
    private final String buyerName;
    private final BigDecimal totalAmount;
    private final int itemCount;

    public OrderPlacedEvent(Long orderId, Long buyerId, String buyerEmail, String buyerName, BigDecimal totalAmount, int itemCount) {
        this.orderId = orderId;
        this.buyerId = buyerId;
        this.buyerEmail = buyerEmail;
        this.buyerName = buyerName;
        this.totalAmount = totalAmount;
        this.itemCount = itemCount;
    }
    public Long getOrderId() { return orderId; }
    public Long getBuyerId() { return buyerId; }
    public String getBuyerEmail() { return buyerEmail; }
    public String getBuyerName() { return buyerName; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public int getItemCount() { return itemCount; }

}
