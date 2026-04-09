package com.farmSphere.core.event.investment;

import java.math.BigDecimal;

public class TokenSoldEvent {
    private final Long listingId;
    private final Long sellerId;
    private final String sellerEmail;
    private final Long buyerId;
    private final String buyerEmail;
    private final String cropName;
    private final int units;
    private final BigDecimal totalAmount;

    public TokenSoldEvent(Long listingId, Long sellerId, String sellerEmail, Long buyerId, String buyerEmail, String cropName, int units, BigDecimal totalAmount) {
        this.listingId = listingId;
        this.sellerId = sellerId;
        this.sellerEmail = sellerEmail;
        this.buyerId = buyerId;
        this.buyerEmail = buyerEmail;
        this.cropName = cropName;
        this.units = units;
        this.totalAmount = totalAmount;
    }

    public Long getListingId() { return listingId; }
    public Long getSellerId() { return sellerId; }
    public String getSellerEmail() { return sellerEmail; }
    public Long getBuyerId() { return buyerId; }
    public String getBuyerEmail() { return buyerEmail; }
    public String getCropName() { return cropName; }
    public int getUnits() { return units; }
    public BigDecimal getTotalAmount() { return totalAmount; }
}
