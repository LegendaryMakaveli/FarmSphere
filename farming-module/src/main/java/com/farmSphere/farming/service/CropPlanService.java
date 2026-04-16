package com.farmSphere.farming.service;

import com.farmSphere.core.dto.CropPlanSummary;
import com.farmSphere.farming.dto.request.AddIntercropRequest;
import com.farmSphere.farming.dto.request.CreateCropPlanRequest;
import com.farmSphere.farming.dto.response.CropPlanItemResponse;
import com.farmSphere.farming.dto.response.CropPlanResponse;

import java.util.List;

public interface CropPlanService {
    CropPlanResponse createCropPlan(CreateCropPlanRequest request);
    CropPlanItemResponse addIntercrop(Long plotId, Long farmerId, AddIntercropRequest request);
    CropPlanItemResponse recordActualYield(Long itemId, float actualYield);
    CropPlanResponse getCropPlanByPlot(Long plotId);
    CropPlanResponse getCropPlanById(Long cropPlanId);
    CropPlanSummary getCropPlanSummary(Long cropPlanId);
    List<CropPlanResponse> getAllCropPlans();
}
