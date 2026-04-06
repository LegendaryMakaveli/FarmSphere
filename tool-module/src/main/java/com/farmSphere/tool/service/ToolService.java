package com.farmSphere.tool.service;

import com.farmSphere.tool.dto.request.AddStockRequest;
import com.farmSphere.tool.dto.request.CreateToolRequest;
import com.farmSphere.tool.dto.request.UpdateToolRequest;
import com.farmSphere.tool.dto.response.ToolResponse;

import java.util.List;

public interface ToolService {
    ToolResponse createTool(CreateToolRequest request);
    ToolResponse addStock(Long toolId, AddStockRequest request);
    ToolResponse updateTool(Long toolId, UpdateToolRequest request);
    List<ToolResponse> getAvailableTools();
    List<ToolResponse> getAllTools();
    ToolResponse getToolById(Long toolId);
}
