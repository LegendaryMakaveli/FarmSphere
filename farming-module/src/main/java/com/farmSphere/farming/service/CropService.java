package com.farmSphere.farming.service;

import com.farmSphere.core.enums.PRODUCE_CATEGORY;
import com.farmSphere.farming.dto.request.CreateCropRequest;
import com.farmSphere.farming.dto.response.CropResponse;

import java.util.List;

public interface CropService {
    CropResponse createCrop(CreateCropRequest request);
    List<CropResponse> getAllCrops();
    CropResponse getCropById(Long cropId);
    List<CropResponse> getCropsByCategory(PRODUCE_CATEGORY category);

}
