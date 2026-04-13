package com.farmSphere.farming.service;


import com.farmSphere.core.enums.CROP_ITEM_ROLE;
import com.farmSphere.core.enums.FARMING_STATUS;
import com.farmSphere.core.event.farming.FarmCycleStartedEvent;
import com.farmSphere.core.event.farming.FarmCycleHarvestedEvent;
import com.farmSphere.farming.data.model.CropPlan;
import com.farmSphere.farming.data.model.CropPlanItem;
import com.farmSphere.farming.data.model.FarmCycle;
import com.farmSphere.farming.data.repository.CropPlanItemRepository;
import com.farmSphere.farming.data.repository.CropPlanRepository;
import com.farmSphere.farming.data.repository.FarmCycleRepository;
import com.farmSphere.farming.dto.request.RecordHarvestRequest;
import com.farmSphere.farming.dto.request.StartFarmCycleRequest;
import com.farmSphere.farming.dto.response.FarmCycleResponse;
import com.farmSphere.infrastructure.eventbus.DomainEventPublisher;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.infrastructure.security.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.farmSphere.farming.util.Mapper.getFarmCycle;

@Service
@RequiredArgsConstructor
public class FarmCycleServiceImplementation implements FarmCycleService{

    private final FarmCycleRepository farmCycleRepository;
    private final CropPlanRepository cropPlanRepository;
    private final CropPlanItemRepository cropPlanItemRepository;
    private final DomainEventPublisher eventPublisher;



    @Transactional
    @Override
    public FarmCycleResponse startFarmCycle(StartFarmCycleRequest request) {
        SecurityUtils.requireAdmin();

        CropPlan plan = cropPlanRepository.findByPlotId(request.getPlotId()).orElseThrow(() -> new DomainException("No crop plan found for this plot — create one first", 404));
        farmCycleRepository.findByPlotIdAndStatus(request.getPlotId(), FARMING_STATUS.ACTIVE).ifPresent(existing -> {throw new DomainException("An active farm cycle already exists for this plot", 409);});

        FarmCycle cycle = getFarmCycle(request, plan);
        FarmCycle saved = farmCycleRepository.save(cycle);

        String primaryCropName = cropPlanItemRepository.findByCropPlanIdAndRole(plan.getCropPlanId(), CROP_ITEM_ROLE.PRIMARY).map(CropPlanItem::getCropName).orElse("Unknown crop");

        eventPublisher.publish(new FarmCycleStartedEvent(
                saved.getFarmCycleId(),
                saved.getPlotId(),
                saved.getFarmerId(),
                saved.getFarmerEmail(),
                primaryCropName
        ));

        return FarmCycleResponse.from(saved);
    }

    @Transactional
    @Override
    public FarmCycleResponse activateFarmCycle(Long farmCycleId) {
        SecurityUtils.requireAdmin();
        FarmCycle cycle = farmCycleRepository.findById(farmCycleId).orElseThrow(() -> new DomainException("FarmCycle not found", 404));
        if (cycle.getStatus() != FARMING_STATUS.PLANNED) throw new DomainException("Only PLANNED cycles can be activated", 400);

        cycle.setStatus(FARMING_STATUS.ACTIVE);
        return FarmCycleResponse.from(farmCycleRepository.save(cycle));    }

    @Transactional
    @Override
    public FarmCycleResponse recordHarvest(Long farmCycleId, RecordHarvestRequest request) {
        SecurityUtils.requireAdmin();
        FarmCycle cycle = farmCycleRepository.findById(farmCycleId).orElseThrow(() -> new DomainException("FarmCycle not found", 404));
        if (cycle.getStatus() != FARMING_STATUS.ACTIVE) throw new DomainException("Only ACTIVE cycles can be harvested", 400);

        for (RecordHarvestRequest.ItemYield itemYield : request.getItemYields()) {
            CropPlanItem item = cropPlanItemRepository.findById(itemYield.getItemId()).orElseThrow(() -> new DomainException("CropPlanItem not found: " + itemYield.getItemId(), 404));
            item.setActualYield(itemYield.getActualYield());
            cropPlanItemRepository.save(item);
        }

        float totalPrimaryYield = (float) cropPlanItemRepository.findAllByCropPlanIdAndRole(cycle.getCropPlanId(), CROP_ITEM_ROLE.PRIMARY)
                .stream()
                .mapToDouble(CropPlanItem::getActualYield)
                .sum();

        String primaryCropName = cropPlanItemRepository.findByCropPlanIdAndRole(cycle.getCropPlanId(), CROP_ITEM_ROLE.PRIMARY).map(CropPlanItem::getCropName).orElse("Unknown");
        cycle.setStatus(FARMING_STATUS.HARVESTED);
        cycle.setEndDate(LocalDateTime.now());
        FarmCycle saved = farmCycleRepository.save(cycle);

        eventPublisher.publish(new FarmCycleHarvestedEvent(
                saved.getFarmCycleId(),
                saved.getPlotId(),
                saved.getCropPlanId(),
                totalPrimaryYield,
                primaryCropName
        ));

        return FarmCycleResponse.from(saved);
    }

    @Override
    public List<FarmCycleResponse> getMyFarmCycles(Long farmerId) {
        SecurityUtils.requireFarmer();
        return farmCycleRepository.findAllByFarmerId(farmerId).stream().map(FarmCycleResponse::from).toList();
    }

    @Override
    public List<FarmCycleResponse> getAllFarmCycles() {
        SecurityUtils.requireAdmin();
        return farmCycleRepository.findAll().stream().map(FarmCycleResponse::from).toList();
    }

    @Override
    public FarmCycleResponse getActiveCycleByPlot(Long plotId) {
        return farmCycleRepository.findByPlotIdAndStatus(plotId, FARMING_STATUS.ACTIVE)
                .map(FarmCycleResponse::from)
                .orElseThrow(() -> new DomainException("No active farm cycle for this plot", 404));
    }
}
