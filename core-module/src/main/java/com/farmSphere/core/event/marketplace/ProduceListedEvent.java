package com.farmSphere.core.event.marketplace;

import java.math.BigDecimal;

public class ProduceListedEvent {
    private final Long produceId;
    private final Long farmerId;
    private final String farmerName;
    private final String cropName;
    private final float quantity;
    private final String unit;
    private final BigDecimal pricePerUnit;

    public ProduceListedEvent(Long produceId, Long farmerId, String farmerName,
                              String cropName, float quantity,
                              String unit, BigDecimal pricePerUnit) {
        this.produceId = produceId;
        this.farmerId = farmerId;
        this.farmerName = farmerName;
        this.cropName = cropName;
        this.quantity = quantity;
        this.unit = unit;
        this.pricePerUnit = pricePerUnit;
    }
    public Long getProduceId() { return produceId; }
    public Long getFarmerId() { return farmerId; }
    public String getFarmerName() { return farmerName; }
    public String getCropName() { return cropName; }
    public float getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public BigDecimal getPricePerUnit() { return pricePerUnit;
    }
}
