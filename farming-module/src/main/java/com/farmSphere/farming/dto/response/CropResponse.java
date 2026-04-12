package com.farmSphere.farming.dto.response;

import com.farmSphere.farming.data.model.Crop;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CropResponse {
    private Long cropId;
    private String cropName;
    private String category;
    private int growthDurationDays;
    private String description;

    public static CropResponse from(Crop crop) {
        return CropResponse.builder()
                .cropId(crop.getCropId())
                .cropName(crop.getCropName())
                .category(crop.getCategory().name())
                .growthDurationDays(crop.getGrowthDurationDays())
                .description(crop.getDescription())
                .build();
    }
}
