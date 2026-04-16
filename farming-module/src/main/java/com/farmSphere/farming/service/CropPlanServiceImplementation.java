package com.farmSphere.farming.service;


import com.farmSphere.core.dto.CropPlanSummary;
import com.farmSphere.core.enums.CROP_ITEM_ROLE;
import com.farmSphere.core.event.farming.IntercropAddedEvent;
import com.farmSphere.farming.data.model.Crop;
import com.farmSphere.farming.data.model.CropPlan;
import com.farmSphere.farming.data.model.CropPlanItem;
import com.farmSphere.farming.data.repository.CropPlanItemRepository;
import com.farmSphere.farming.data.repository.CropPlanRepository;
import com.farmSphere.farming.data.repository.CropRepository;
import com.farmSphere.farming.dto.request.AddIntercropRequest;
import com.farmSphere.farming.dto.request.CreateCropPlanRequest;
import com.farmSphere.farming.dto.response.CropPlanItemResponse;
import com.farmSphere.farming.dto.response.CropPlanResponse;
import com.farmSphere.infrastructure.eventbus.DomainEventPublisher;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.infrastructure.security.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.farmSphere.farming.util.Mapper.*;

@Service
@RequiredArgsConstructor
public class CropPlanServiceImplementation implements CropPlanService{
    private final CropPlanRepository cropPlanRepository;
    private final CropPlanItemRepository cropPlanItemRepository;
    private final CropRepository cropRepository;
    private final DomainEventPublisher eventPublisher;


    @Transactional
    @Override
    public CropPlanResponse createCropPlan(CreateCropPlanRequest request) {
        SecurityUtils.requireAdmin();
        if (cropPlanRepository.existsByPlotId(request.getPlotId())) throw new DomainException("A crop plan already exists for this plot", 409);
        Crop primaryCrop = cropRepository.findById(request.getPrimaryCropId()).orElseThrow(() -> new DomainException("Crop not found", 404));

        CropPlan plan = getCropPlan(request);
        CropPlan savedPlan = cropPlanRepository.save(plan);

        CropPlanItem primaryItem = getCropPlanItem(request, savedPlan, primaryCrop);

        cropPlanItemRepository.save(primaryItem);

        return CropPlanResponse.from(savedPlan, cropPlanItemRepository.findAllByCropPlanId(savedPlan.getCropPlanId()));    }

    @Transactional
    @Override
    public CropPlanItemResponse addIntercrop(Long plotId, Long farmerId, AddIntercropRequest request) {
        SecurityUtils.requireFarmer();
        if (!plotId.equals(request.getPlotId())) throw new DomainException("Plot ID mismatch", 400);

        CropPlan plan = cropPlanRepository.findByPlotId(plotId).orElseThrow(() -> new DomainException("No crop plan found for this plot", 404));
        if (!plan.isIntercroppingEnabled()) throw new DomainException("Intercropping is not enabled for this plot", 400);

        if (cropPlanItemRepository.existsByCropPlanIdAndRole(plan.getCropPlanId(), CROP_ITEM_ROLE.INTERCROP)) throw new DomainException("An intercrop is already added to this plan", 409);
        Crop crop = cropRepository.findById(request.getIntercropCropId()).orElseThrow(() -> new DomainException("Crop not found", 404));

        CropPlanItem intercrop = getIntercrop(request, plan, crop);
        CropPlanItem saved = cropPlanItemRepository.save(intercrop);

        eventPublisher.publish(new IntercropAddedEvent(
                plan.getCropPlanId(), plotId, farmerId, crop.getCropName()));

        return CropPlanItemResponse.from(saved);    }


    @Transactional
    @Override
    public CropPlanItemResponse recordActualYield(Long itemId, float actualYield) {
        SecurityUtils.requireAdmin();

        CropPlanItem item = cropPlanItemRepository.findById(itemId).orElseThrow(() -> new DomainException("CropPlanItem not found", 404));
        item.setActualYield(actualYield);

        return CropPlanItemResponse.from(cropPlanItemRepository.save(item));    }


    @Override
    public CropPlanResponse getCropPlanByPlot(Long plotId) {
        CropPlan plan = cropPlanRepository.findByPlotId(plotId).orElseThrow(() -> new DomainException("No crop plan for this plot", 404));

        return CropPlanResponse.from(plan, cropPlanItemRepository.findAllByCropPlanId(plan.getCropPlanId()));    }

    @Override
    public CropPlanResponse getCropPlanById(Long cropPlanId) {
        CropPlan plan = cropPlanRepository.findById(cropPlanId).orElseThrow(() -> new DomainException("CropPlan not found", 404));
        return CropPlanResponse.from(plan, cropPlanItemRepository.findAllByCropPlanId(cropPlanId));    }

    @Override
    public CropPlanSummary getCropPlanSummary(Long cropPlanId) {
        CropPlan plan = cropPlanRepository.findById(cropPlanId).orElseThrow(() -> new DomainException("CropPlan not found", 404));
        List<CropPlanItem> items = cropPlanItemRepository.findAllByCropPlanId(cropPlanId);

        return cropPlanSummary(plan, items);
    }

    @Transactional
    @Override
    public CropPlanResponse enableIntercropping(Long plotId) {
        SecurityUtils.requireAdmin();
        CropPlan plan = cropPlanRepository.findByPlotId(plotId)
                .orElseThrow(() -> new DomainException("No crop plan found for this plot", 404));
        
        if (plan.isIntercroppingEnabled()) {
            throw new DomainException("Intercropping is already enabled for this plot", 409);
        }

        plan.setIntercroppingEnabled(true);
        CropPlan saved = cropPlanRepository.save(plan);
        
        return CropPlanResponse.from(saved, cropPlanItemRepository.findAllByCropPlanId(saved.getCropPlanId()));
    }


    @Override
    public List<CropPlanResponse> getAllCropPlans() {
        return cropPlanRepository.findAll().stream()
                .map(plan -> CropPlanResponse.from(plan, cropPlanItemRepository.findAllByCropPlanId(plan.getCropPlanId())))
                .toList();
    }


}
