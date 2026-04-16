package com.farmSphere.core.dto;

import com.farmSphere.core.enums.YIELD_UNIT;

import java.util.List;

public class CropPlanSummary {
    private Long cropPlanId;
    private Long plotId;
    private boolean intercroppingEnabled;
    private List<CropPlanItemSummary> items;

    public static class CropPlanItemSummary {
        private Long cropId;
        private String cropName;
        private String role;
        private float expectedYield;
        private float actualYield;
        private YIELD_UNIT yieldUnit;


        public Long getCropId() { return cropId; }
        public void setCropId(Long cropId) { this.cropId = cropId; }
        public String getCropName() { return cropName; }
        public void setCropName(String cropName) { this.cropName = cropName; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        public float getExpectedYield() { return expectedYield; }
        public void setExpectedYield(float expectedYield) { this.expectedYield = expectedYield; }
        public float getActualYield() { return actualYield; }
        public void setActualYield(float actualYield) { this.actualYield = actualYield; }
        public YIELD_UNIT getYieldUnit() { return yieldUnit; }
        public void setYieldUnit(YIELD_UNIT yieldUnit) { this.yieldUnit = yieldUnit; }
    }

    public Long getCropPlanId() { return cropPlanId; }
    public void setCropPlanId(Long cropPlanId) { this.cropPlanId = cropPlanId; }
    public Long getPlotId() { return plotId; }
    public void setPlotId(Long plotId) { this.plotId = plotId; }
    public boolean isIntercroppingEnabled() { return intercroppingEnabled; }
    public void setIntercroppingEnabled(boolean v) { this.intercroppingEnabled = v; }
    public List<CropPlanItemSummary> getItems() { return items; }
    public void setItems(List<CropPlanItemSummary> items) { this.items = items; }
}