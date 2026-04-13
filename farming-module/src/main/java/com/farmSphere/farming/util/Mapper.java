package com.farmSphere.farming.util;

import com.farmSphere.core.dto.CropPlanSummary;
import com.farmSphere.core.enums.CROP_ITEM_ROLE;
import com.farmSphere.core.enums.FARMING_STATUS;
import com.farmSphere.core.enums.TASK_STATUS;
import com.farmSphere.farming.data.model.*;
import com.farmSphere.farming.dto.request.*;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public class Mapper {

    @NonNull
    public static Crop mapToCreateCrop(CreateCropRequest request) {
        Crop crop = new Crop();
        crop.setCropName(request.getCropName().trim());
        crop.setCategory(request.getCategory());
        crop.setGrowthDurationDays(request.getGrowthDurationDays());
        crop.setDescription(request.getDescription());
        return crop;
    }

    @NonNull
    public static CropPlan getCropPlan(CreateCropPlanRequest request) {
        CropPlan plan = new CropPlan();
        plan.setPlotId(request.getPlotId());
        plan.setIntercroppingEnabled(request.isIntercroppingEnabled());
        plan.setCreatedAt(LocalDateTime.now());
        return plan;
    }

    @NonNull
    public static CropPlanItem getCropPlanItem(CreateCropPlanRequest request, CropPlan savedPlan, Crop primaryCrop) {
        CropPlanItem primaryItem = new CropPlanItem();
        primaryItem.setCropPlanId(savedPlan.getCropPlanId());
        primaryItem.setCropId(primaryCrop.getCropId());
        primaryItem.setCropName(primaryCrop.getCropName());
        primaryItem.setRole(CROP_ITEM_ROLE.PRIMARY);
        primaryItem.setExpectedYield(request.getExpectedYield());
        primaryItem.setYieldUnit(request.getYieldUnit());
        primaryItem.setSpacingPattern(request.getSpacingPattern());
        primaryItem.setCreatedAt(LocalDateTime.now());
        return primaryItem;
    }

    @NonNull
    public static CropPlanItem getIntercrop(AddIntercropRequest request, CropPlan plan, Crop crop) {
        CropPlanItem intercrop = new CropPlanItem();
        intercrop.setCropPlanId(plan.getCropPlanId());
        intercrop.setCropId(crop.getCropId());
        intercrop.setCropName(crop.getCropName());
        intercrop.setRole(CROP_ITEM_ROLE.INTERCROP);
        intercrop.setExpectedYield(request.getExpectedYield());
        intercrop.setYieldUnit(request.getYieldUnit());
        intercrop.setSpacingPattern(request.getSpacingPattern());
        intercrop.setCreatedAt(LocalDateTime.now());
        return intercrop;
    }

    @NonNull
    public static CropPlanSummary.CropPlanItemSummary getSummary(CropPlanItem item) {
        CropPlanSummary.CropPlanItemSummary summary = new CropPlanSummary.CropPlanItemSummary();
        summary.setCropId(item.getCropId());
        summary.setCropName(item.getCropName());
        summary.setRole(item.getRole().name());
        summary.setExpectedYield(item.getExpectedYield());
        summary.setActualYield(item.getActualYield());
        summary.setYieldUnit(item.getYieldUnit());
        return summary;
    }

    @NonNull
    public static CropPlanSummary cropPlanSummary(CropPlan plan, List<CropPlanItem> items) {
        CropPlanSummary dto = new CropPlanSummary();
        dto.setCropPlanId(plan.getCropPlanId());
        dto.setPlotId(plan.getPlotId());
        dto.setIntercroppingEnabled(plan.isIntercroppingEnabled());
        dto.setItems(items.stream().map(item -> getSummary(item)).toList());
        return dto;
    }

    @NonNull
    public static FarmCycle getFarmCycle(StartFarmCycleRequest request, CropPlan plan) {
        FarmCycle cycle = new FarmCycle();
        cycle.setPlotId(request.getPlotId());
        cycle.setCropPlanId(plan.getCropPlanId());
        cycle.setFarmerId(request.getFarmerId());
        cycle.setFarmerEmail(request.getFarmerEmail());
        cycle.setStatus(FARMING_STATUS.PLANNED);
        cycle.setStartDate(request.getStartDate());
        cycle.setCreatedAt(LocalDateTime.now());
        return cycle;
    }

    @NonNull
    public static Task getTask(CreateTaskRequest request) {
        Task task = new Task();
        task.setFarmCycleId(request.getFarmCycleId());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setAssignedFarmerId(request.getAssignedFarmerId());
        task.setAssignedFarmerEmail(request.getAssignedFarmerEmail());
        task.setStatus(TASK_STATUS.PENDING);
        return task;
    }

}
