package com.farmSphere.core.event.farming;

public class IntercropAddedEvent {
    private final Long cropPlanId;
    private final Long plotId;
    private final Long farmerId;
    private final String cropName;

    public IntercropAddedEvent(Long cropPlanId, Long plotId,
                               Long farmerId, String cropName) {
        this.cropPlanId = cropPlanId;
        this.plotId = plotId;
        this.farmerId = farmerId;
        this.cropName = cropName;
    }

    public Long getCropPlanId() { return cropPlanId; }
    public Long getPlotId() { return plotId; }
    public Long getFarmerId() { return farmerId; }
    public String getCropName() { return cropName; }
}
