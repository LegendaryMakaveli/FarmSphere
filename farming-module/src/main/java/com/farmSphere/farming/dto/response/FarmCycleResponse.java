package com.farmSphere.farming.dto.response;


import com.farmSphere.farming.data.model.FarmCycle;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class FarmCycleResponse {
    private Long farmCycleId;
    private Long plotId;
    private Long cropPlanId;
    private Long farmerId;
    private String status;
    private String startDate;
    private String endDate;
    private String createdAt;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static FarmCycleResponse from(FarmCycle cycle) {
        return FarmCycleResponse.builder()
                .farmCycleId(cycle.getFarmCycleId())
                .plotId(cycle.getPlotId())
                .cropPlanId(cycle.getCropPlanId())
                .farmerId(cycle.getFarmerId())
                .status(cycle.getStatus().name())
                .startDate(FORMATTER.format(cycle.getStartDate()))
                .endDate(cycle.getEndDate() != null ? FORMATTER.format(cycle.getEndDate()) : null)
                .createdAt(FORMATTER.format(cycle.getCreatedAt()))
                .build();
    }
}
