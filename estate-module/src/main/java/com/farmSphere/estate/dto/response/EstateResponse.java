package com.farmSphere.estate.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstateResponse {
    private Long estateId;
    private String name;
    private String location;
    private float totalSize;
    private String description;
    private String createdAt;
}
