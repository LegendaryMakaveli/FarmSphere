package com.farmSphere.core.event.investment;

import java.math.BigDecimal;

public class InvestorPaidEvent {
    private final Long investorId;
    private final String investorEmail;
    private final String investorName;
    private final String cropName;
    private final int units;
    private final BigDecimal totalInvested;
    private final BigDecimal roiEarned;
    private final BigDecimal totalPayout;
    private final float actualROI;

    public InvestorPaidEvent(Long investorId, String investorEmail, String investorName, String cropName, int units, BigDecimal totalInvested, BigDecimal roiEarned, BigDecimal totalPayout, float actualROI) {
        this.investorId = investorId;
        this.investorEmail = investorEmail;
        this.investorName = investorName;
        this.cropName = cropName;
        this.units = units;
        this.totalInvested = totalInvested;
        this.roiEarned = roiEarned;
        this.totalPayout = totalPayout;
        this.actualROI = actualROI;
    }

    public Long getInvestorId() { return investorId; }
    public String getInvestorEmail() { return investorEmail; }
    public String getInvestorName() { return investorName; }
    public String getCropName() { return cropName; }
    public int getUnits() { return units; }
    public BigDecimal getTotalInvested() { return totalInvested; }
    public BigDecimal getRoiEarned() { return roiEarned; }
    public BigDecimal getTotalPayout() { return totalPayout; }
    public float getActualROI() { return actualROI; }
}
