package com.farmSphere.core.event.farming;

public class FarmCycleStartedEvent {
    private final Long farmCycleId;
    private final Long plotId;
    private final Long farmerId;
    private final String farmerEmail;
    private final String primaryCropName;

    public FarmCycleStartedEvent(Long farmCycleId, Long plotId, Long farmerId, String farmerEmail, String primaryCropName) {
        this.farmCycleId = farmCycleId;
        this.plotId = plotId;
        this.farmerId = farmerId;
        this.farmerEmail = farmerEmail;
        this.primaryCropName = primaryCropName;
    }

    public Long getFarmCycleId() { return farmCycleId; }
    public Long getPlotId() { return plotId; }
    public Long getFarmerId() { return farmerId; }
    public String getFarmerEmail() { return farmerEmail; }
    public String getPrimaryCropName() { return primaryCropName; }
}
