package com.farmSphere.farming.data.repository;

import com.farmSphere.core.enums.FARMING_STATUS;
import com.farmSphere.farming.data.model.FarmCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FarmCycleRepository extends JpaRepository<FarmCycle, Long> {
    Optional<FarmCycle> findByPlotId(Long plotId);
    Optional<FarmCycle> findByPlotIdAndStatus(Long plotId, FARMING_STATUS status);
    Optional<FarmCycle> findByCropPlanId(Long cropPlanId);
    List<FarmCycle> findAllByFarmerId(Long farmerId);
    List<FarmCycle> findAllByStatus(FARMING_STATUS status);
}
