package com.farmSphere.tool.dto.response;

import com.farmSphere.tool.data.model.Tool;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ToolResponse {
    private Long toolId;
    private String toolName;
    private String description;
    private int quantityAvailable;
    private String conditionStatus;
    private boolean availabilityStatus;

    public static ToolResponse from(Tool tool) {
        return ToolResponse.builder()
                .toolId(tool.getToolId())
                .toolName(tool.getToolName())
                .description(tool.getDescription())
                .quantityAvailable(tool.getQuantityAvailable())
                .conditionStatus(tool.getConditionStatus())
                .availabilityStatus(tool.isAvailabilityStatus())
                .build();
    }
}
