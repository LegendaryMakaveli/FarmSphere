package com.farmSphere.marketplace.service;


import com.farmSphere.core.enums.PRODUCE_CATEGORY;
import com.farmSphere.core.enums.PRODUCE_STATUS;
import com.farmSphere.core.event.marketplace.ProduceListedEvent;
import com.farmSphere.infrastructure.eventbus.DomainEventPublisher;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.infrastructure.security.SecurityUtils;
import com.farmSphere.marketplace.data.model.Produce;
import com.farmSphere.marketplace.data.repository.ProduceRepository;
import com.farmSphere.marketplace.dto.request.ListProduceRequest;
import com.farmSphere.marketplace.dto.request.UpdateProduceRequest;
import com.farmSphere.marketplace.dto.response.ProduceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.farmSphere.marketplace.util.Validation.mapToListProduce;

@Service
@RequiredArgsConstructor
public class ProduceServiceImplementation implements ProduceService{

    private final ProduceRepository produceRepository;
    private final DomainEventPublisher eventPublisher;

    @Override
    public ProduceResponse listProduce(Long farmerId, String farmerName, String farmerEmail, ListProduceRequest request) {
        SecurityUtils.requireFarmer();
        if (!request.getExpiryDate().isAfter(request.getHarvestDate())) throw new DomainException("Expiry date must be after harvest date", 400);
        if (request.getExpiryDate().isBefore(LocalDate.now())) throw new DomainException("Expiry date must be in the future", 400);

        Produce produce = mapToListProduce(farmerId, farmerName, farmerEmail, request);

        Produce saved = produceRepository.save(produce);

        eventPublisher.publish(new ProduceListedEvent(
                saved.getProduceId(),
                saved.getFarmerId(),
                saved.getFarmerName(),
                saved.getCropName(),
                saved.getQuantityAvailable(),
                saved.getUnit(),
                saved.getPricePerUnit()
        ));

        return ProduceResponse.from(saved);
    }

    @Override
    public ProduceResponse updateProduce(Long produceId, Long farmerId, UpdateProduceRequest request) {
        SecurityUtils.requireFarmer();
        Produce produce = produceRepository.findByProduceIdAndFarmerId(produceId, farmerId).orElseThrow(() -> new DomainException("Produce not found", 404));
        if (request.getQuantityAvailable() != null) {
            produce.setQuantityAvailable(request.getQuantityAvailable());
            if (request.getQuantityAvailable() == 0) {
                produce.setStatus(PRODUCE_STATUS.SOLD_OUT);
            }
        }

        if (request.getPricePerUnit() != null) produce.setPricePerUnit(request.getPricePerUnit());
        if (request.getDescription() != null) produce.setDescription(request.getDescription());
        if (request.getImageUrl() != null) produce.setImageUrl(request.getImageUrl());
        if (request.getStatus() != null) produce.setStatus(request.getStatus());
        produce.setUpdatedAt(LocalDateTime.now());

        return ProduceResponse.from(produceRepository.save(produce));    }

    @Override
    public void deleteProduce(Long produceId, Long farmerId) {
        SecurityUtils.requireFarmer();
        Produce produce = produceRepository.findByProduceIdAndFarmerId(produceId, farmerId).orElseThrow(() -> new DomainException("Produce not found", 404));
        if (produce.getStatus() != PRODUCE_STATUS.AVAILABLE) throw new DomainException("Cannot delete produce with status: " + produce.getStatus().name(), 400);
        produceRepository.delete(produce);
    }

    @Override
    public List<ProduceResponse> getMyProduce(Long farmerId) {
        SecurityUtils.requireFarmer();
        return produceRepository.findAllByFarmerId(farmerId)
                .stream()
                .map(ProduceResponse::from)
                .toList();
    }

    @Override
    public List<ProduceResponse> getAllAvailableProduce() {
        SecurityUtils.requireUser();
        return produceRepository.findAllByStatus(PRODUCE_STATUS.AVAILABLE)
                .stream()
                .map(ProduceResponse::from)
                .toList();
    }

    @Override
    public List<ProduceResponse> getByCategory(PRODUCE_CATEGORY category) {
        SecurityUtils.requireUser();
        return produceRepository
                .findAllByStatusAndCategory(PRODUCE_STATUS.AVAILABLE, category)
                .stream()
                .map(ProduceResponse::from)
                .toList();
    }

    @Override
    public ProduceResponse getProduceById(Long produceId) {
        SecurityUtils.requireUser();
        return produceRepository.findById(produceId)
                .map(ProduceResponse::from)
                .orElseThrow(() -> new DomainException("Produce not found", 404));
    }
}
