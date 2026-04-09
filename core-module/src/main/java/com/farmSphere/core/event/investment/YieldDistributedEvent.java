package com.farmSphere.core.event.investment;

import java.math.BigDecimal;

public class YieldDistributedEvent {
    private final Long assetId;
    private final String cropName;
    private final BigDecimal totalPaidOut;
    private final int totalInvestorsPaid;
    private final float actualROI;

    public YieldDistributedEvent(Long assetId, String cropName, BigDecimal totalPaidOut, int totalInvestorsPaid, float actualROI) {
        this.assetId = assetId;
        this.cropName = cropName;
        this.totalPaidOut = totalPaidOut;
        this.totalInvestorsPaid = totalInvestorsPaid;
        this.actualROI = actualROI;
    }

    public Long getAssetId() { return assetId; }
    public String getCropName() { return cropName; }
    public BigDecimal getTotalPaidOut() { return totalPaidOut; }
    public int getTotalInvestorsPaid() { return totalInvestorsPaid; }
    public float getActualROI() { return actualROI; }
}
