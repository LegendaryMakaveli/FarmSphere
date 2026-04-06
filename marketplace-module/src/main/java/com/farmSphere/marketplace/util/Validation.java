package com.farmSphere.marketplace.util;

import com.farmSphere.core.enums.PRODUCE_STATUS;
import com.farmSphere.marketplace.data.model.Produce;
import com.farmSphere.marketplace.dto.request.ListProduceRequest;

import java.time.LocalDateTime;

public class Validation {

    public static Produce mapToListProduce(Long farmerId, String farmerName, String farmerEmail, ListProduceRequest request) {
        Produce produce = new Produce();
        produce.setFarmerId(farmerId);
        produce.setFarmerName(farmerName);
        produce.setFarmerEmail(farmerEmail);
        produce.setCropName(request.getCropName().trim());
        produce.setCategory(request.getCategory());
        produce.setQuantityAvailable(request.getQuantityAvailable());
        produce.setUnit(request.getUnit().toUpperCase().trim());
        produce.setPricePerUnit(request.getPricePerUnit());
        produce.setHarvestDate(request.getHarvestDate());
        produce.setExpiryDate(request.getExpiryDate());
        produce.setDescription(request.getDescription());
        produce.setImageUrl(request.getImageUrl());
        produce.setStatus(PRODUCE_STATUS.AVAILABLE);
        produce.setListedAt(LocalDateTime.now());
        return produce;
    }
}
