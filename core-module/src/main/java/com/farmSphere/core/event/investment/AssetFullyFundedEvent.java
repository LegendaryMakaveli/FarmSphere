package com.farmSphere.core.event.investment;

public class AssetFullyFundedEvent {
    private final Long assetId;
    private final Long cropPlanId;
    private final String cropName;

    public AssetFullyFundedEvent(Long assetId, Long cropPlanId, String cropName) {
        this.assetId = assetId;
        this.cropPlanId = cropPlanId;
        this.cropName = cropName;
    }

    public Long getAssetId() {return assetId;}
    public Long getCropPlanId() {return cropPlanId;}
    public String getCropName() {return cropName;}
}
