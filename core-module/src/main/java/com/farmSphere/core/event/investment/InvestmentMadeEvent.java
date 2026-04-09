package com.farmSphere.core.event.investment;

import java.math.BigDecimal;


public class InvestmentMadeEvent {
    private final Long tokenId;
    private final Long investorId;
    private final String investorEmail;
    private final String investorName;
    private final Long assetId;
    private final String cropName;
    private final int unitsPurchased;
    private final BigDecimal totalInvested;

    public InvestmentMadeEvent(Long tokenId, Long investorId, String investorEmail, String investorName, Long assetId, String cropName, int unitsPurchased, BigDecimal totalInvested) {
        this.tokenId = tokenId;
        this.investorId = investorId;
        this.investorEmail = investorEmail;
        this.investorName = investorName;
        this.assetId = assetId;
        this.cropName = cropName;
        this.unitsPurchased = unitsPurchased;
        this.totalInvested = totalInvested;
    }

    public Long getTokenId() { return tokenId; }
    public Long getInvestorId() { return investorId; }
    public String getInvestorEmail() { return investorEmail; }
    public String getInvestorName() { return investorName; }
    public Long getAssetId() { return assetId; }
    public String getCropName() { return cropName; }
    public int getUnitsPurchased() { return unitsPurchased; }
    public BigDecimal getTotalInvested() { return totalInvested; }
}
