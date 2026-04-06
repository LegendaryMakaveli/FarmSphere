package com.farmSphere.marketplace.service;

import com.farmSphere.core.enums.PRODUCE_CATEGORY;
import com.farmSphere.marketplace.dto.request.ListProduceRequest;
import com.farmSphere.marketplace.dto.request.UpdateProduceRequest;
import com.farmSphere.marketplace.dto.response.ProduceResponse;

import java.util.List;

public interface ProduceService {
    ProduceResponse listProduce(Long farmerId, String farmerName, String farmerEmail, ListProduceRequest request);
    ProduceResponse updateProduce(Long produceId, Long farmerId, UpdateProduceRequest request);
    void deleteProduce(Long produceId, Long farmerId);
    List<ProduceResponse> getMyProduce(Long farmerId);
    List<ProduceResponse> getAllAvailableProduce();
    List<ProduceResponse> getByCategory(PRODUCE_CATEGORY category);
    ProduceResponse getProduceById(Long produceId);

}
