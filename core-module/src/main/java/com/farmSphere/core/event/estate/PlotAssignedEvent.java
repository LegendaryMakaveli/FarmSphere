package com.farmSphere.core.event.estate;

public class PlotAssignedEvent {
    private final Long plotId;
    private final Long farmerId;
    private final String farmerEmail;
    private final float plotSize;
    private final String soilType;

    public PlotAssignedEvent(Long plotId, Long farmerId, String farmerEmail, float plotSize, String soilType) {
        this.plotId = plotId;
        this.farmerId = farmerId;
        this.farmerEmail = farmerEmail;
        this.plotSize = plotSize;
        this.soilType = soilType;
    }

    public Long getPlotId() { return plotId; }
    public Long getFarmerId() { return farmerId; }
    public String getFarmerEmail() { return farmerEmail; }
    public float getPlotSize() { return plotSize; }
    public String getSoilType() { return soilType; }
}
