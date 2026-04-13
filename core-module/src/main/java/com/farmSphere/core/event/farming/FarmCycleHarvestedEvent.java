package com.farmSphere.core.event.farming;



public class FarmCycleHarvestedEvent {
    private final Long farmCycleId;
    private final Long plotId;
    private final Long cropPlanId;
    private final float totalPrimaryYieldKg;
    private final String cropName;

    public FarmCycleHarvestedEvent(Long farmCycleId, Long plotId, Long cropPlanId, float totalPrimaryYieldKg, String cropName) {
        this.farmCycleId = farmCycleId;
        this.plotId = plotId;
        this.cropPlanId = cropPlanId;
        this.totalPrimaryYieldKg = totalPrimaryYieldKg;
        this.cropName = cropName;
    }

    public Long getFarmCycleId() { return farmCycleId; }
    public Long getPlotId() { return plotId; }
    public Long getCropPlanId() { return cropPlanId; }
    public float getTotalPrimaryYieldKg() { return totalPrimaryYieldKg; }
    public String getCropName() { return cropName; }
}
