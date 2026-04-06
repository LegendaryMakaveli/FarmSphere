package com.farmSphere.tool.service;


import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.tool.data.model.Tool;
import com.farmSphere.tool.data.repository.ToolRepository;
import com.farmSphere.tool.dto.request.AddStockRequest;
import com.farmSphere.tool.dto.request.CreateToolRequest;
import com.farmSphere.tool.dto.request.UpdateToolRequest;
import com.farmSphere.tool.dto.response.ToolResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.farmSphere.tool.util.Mapper.mapToCreateTool;
import static com.farmSphere.tool.util.Validation.validateTool;

@Service
@RequiredArgsConstructor
public class ToolServiceImplementation implements ToolService{

    private final ToolRepository toolRepository;

    @Override
    public ToolResponse createTool(CreateToolRequest request) {
        validateTool(request);
        toolRepository.findByToolNameIgnoreCase(request.getToolName()).ifPresent(existingTool -> {throw new IllegalArgumentException("Tool with name '" + request.getToolName() + "' already exists.");});
        Tool tool = mapToCreateTool(request);
        return ToolResponse.from(toolRepository.save(tool));
    }

    @Override
    public ToolResponse addStock(Long toolId, AddStockRequest request) {
        Tool tool = toolRepository.findById(toolId).orElseThrow(() -> new DomainException("Tool not found", 404));
        int newQty = tool.getQuantityAvailable() + request.getQuantityToAdd();
        tool.setQuantityAvailable(newQty);
        tool.setAvailabilityStatus(true);

        return ToolResponse.from(toolRepository.save(tool));
    }

    @Override
    public ToolResponse updateTool(Long toolId, UpdateToolRequest request) {
        Tool tool = toolRepository.findById(toolId).orElseThrow(() -> new DomainException("Tool not found", 404));

        if (request.getToolName() != null) tool.setToolName(request.getToolName());
        if (request.getDescription() != null) tool.setDescription(request.getDescription());
        if (request.getConditionStatus() != null) tool.setConditionStatus(request.getConditionStatus());

        return ToolResponse.from(toolRepository.save(tool));
    }

    @Override
    public List<ToolResponse> getAllTools() {
        return toolRepository.findAll()
                .stream()
                .map(ToolResponse::from)
                .toList();
    }

    @Override
    public List<ToolResponse> getAvailableTools() {
        return toolRepository.findAllByAvailabilityStatusTrue()
                .stream()
                .map(ToolResponse::from)
                .toList();
    }

    @Override
    public ToolResponse getToolById(Long toolId) {
        return toolRepository.findById(toolId)
                .map(ToolResponse::from)
                .orElseThrow(() -> new DomainException("Tool not found", 404));
    }
}
