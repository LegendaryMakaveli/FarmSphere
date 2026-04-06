package com.farmSphere.tool.util;

import com.farmSphere.tool.data.model.Tool;
import com.farmSphere.tool.dto.request.CreateToolRequest;

public class Mapper {


    public static Tool mapToCreateTool(CreateToolRequest request) {
        Tool tool = new Tool();
        tool.setToolName(request.getToolName());
        tool.setDescription(request.getDescription());
        tool.setQuantityAvailable(request.getQuantityAvailable());
        tool.setConditionStatus(request.getConditionStatus());
        tool.setAvailabilityStatus(request.getQuantityAvailable() > 0);
        return tool;
    }
}
