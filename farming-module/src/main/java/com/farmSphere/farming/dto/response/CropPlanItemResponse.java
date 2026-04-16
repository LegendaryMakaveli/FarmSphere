package com.farmSphere.farming.dto.response;


import com.farmSphere.core.enums.YIELD_UNIT;
import com.farmSphere.farming.data.model.CropPlanItem;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CropPlanItemResponse {
    private Long itemId;
    private Long cropPlanId;
    private Long cropId;
    private String cropName;
    private String role;
    private String spacingPattern;
    private float expectedYield;
    private YIELD_UNIT yieldUnit;
    private float actualYield;
    private boolean harvested;

    public static CropPlanItemResponse from(CropPlanItem item) {
        return CropPlanItemResponse.builder()
                .itemId(item.getItemId())
                .cropPlanId(item.getCropPlanId())
                .cropId(item.getCropId())
                .cropName(item.getCropName())
                .role(item.getRole().name())
                .spacingPattern(item.getSpacingPattern())
                .expectedYield(item.getExpectedYield())
                .yieldUnit(item.getYieldUnit())
                .actualYield(item.getActualYield())
                .harvested(item.getActualYield() > 0)
                .build();
    }
}
