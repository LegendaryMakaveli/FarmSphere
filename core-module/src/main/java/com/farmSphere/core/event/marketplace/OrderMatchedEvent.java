package com.farmSphere.core.event.marketplace;

public class OrderMatchedEvent {
    private final Long orderId;
    private final Long farmerId;
    private final String farmerEmail;
    private final String cropName;
    private final float quantity;
    private final String unit;

    public OrderMatchedEvent(Long orderId, Long farmerId, String farmerEmail, String cropName, float quantity, String unit) {
        this.orderId = orderId;
        this.farmerId = farmerId;
        this.farmerEmail = farmerEmail;
        this.cropName = cropName;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Long getOrderId() { return orderId; }
    public Long getFarmerId() { return farmerId; }
    public String getFarmerEmail() { return farmerEmail; }
    public String getCropName() { return cropName; }
    public float getQuantity() { return quantity; }
    public String getUnit() { return unit; }
}
