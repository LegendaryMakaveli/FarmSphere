package com.farmSphere.farming.dto.response;


import com.farmSphere.farming.data.model.CropPlan;
import com.farmSphere.farming.data.model.CropPlanItem;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
public class CropPlanResponse {
    private Long cropPlanId;
    private Long plotId;
    private boolean intercroppingEnabled;
    private String createdAt;
    private List<CropPlanItemResponse> items;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static CropPlanResponse from(CropPlan plan, List<CropPlanItem> items) {
        return CropPlanResponse.builder()
                .cropPlanId(plan.getCropPlanId())
                .plotId(plan.getPlotId())
                .intercroppingEnabled(plan.isIntercroppingEnabled())
                .createdAt(FORMATTER.format(plan.getCreatedAt()))
                .items(items.stream()
                        .map(CropPlanItemResponse::from)
                        .toList())
                .build();
    }
}
