package com.farmSphere.core.event.estate;

public class PlotUnassignedEvent {
    private final Long plotId;
    private final Long farmerId;
    private final String farmerEmail;

    public PlotUnassignedEvent(Long plotId, String farmerEmail, Long farmerId) {
        this.plotId = plotId;
        this.farmerEmail = farmerEmail;
        this.farmerId = farmerId;
    }

    public Long getPlotId() { return plotId; }
    public Long getFarmerId() { return farmerId; }
    public String getFarmerEmail() { return farmerEmail; }
}
