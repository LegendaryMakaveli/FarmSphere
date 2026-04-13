package com.farmSphere.farming.service;

import com.farmSphere.farming.dto.request.RecordHarvestRequest;
import com.farmSphere.farming.dto.request.StartFarmCycleRequest;
import com.farmSphere.farming.dto.response.FarmCycleResponse;

import java.util.List;

public interface FarmCycleService {
    FarmCycleResponse startFarmCycle(StartFarmCycleRequest request);
    FarmCycleResponse activateFarmCycle(Long farmCycleId);
    FarmCycleResponse recordHarvest(Long farmCycleId, RecordHarvestRequest request);
    List<FarmCycleResponse> getMyFarmCycles(Long farmerId);
    List<FarmCycleResponse> getAllFarmCycles();
    FarmCycleResponse getActiveCycleByPlot(Long plotId);
}
