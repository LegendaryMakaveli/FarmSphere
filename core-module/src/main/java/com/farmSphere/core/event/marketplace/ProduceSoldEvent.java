package com.farmSphere.core.event.marketplace;

import java.math.BigDecimal;

public class ProduceSoldEvent {
    private final Long farmerId;
    private final String farmerEmail;
    private final String cropName;
    private final float quantity;
    private final BigDecimal earnings;


    public ProduceSoldEvent(Long farmerId, String farmerEmail, String cropName, float quantity, BigDecimal earnings) {
        this.farmerId = farmerId;
        this.farmerEmail = farmerEmail;
        this.cropName = cropName;
        this.quantity = quantity;
        this.earnings = earnings;
    }

    public Long getFarmerId() { return farmerId; }
    public String getFarmerEmail() { return farmerEmail; }
    public String getCropName() { return cropName; }
    public float getQuantity() { return quantity; }
    public BigDecimal getEarnings() { return earnings; }
}
