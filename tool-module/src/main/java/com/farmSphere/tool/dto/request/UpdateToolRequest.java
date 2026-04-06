package com.farmSphere.tool.dto.request;


import lombok.Data;

@Data
public class UpdateToolRequest {
    private String toolName;
    private String description;
    private String conditionStatus;
}
