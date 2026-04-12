package com.farmSphere.farming.data.repository;

import com.farmSphere.farming.data.model.CropPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CropPlanRepository extends JpaRepository<CropPlan, Long> {
    Optional<CropPlan> findByPlotId(Long plotId);   // 1:1 lookup
    boolean existsByPlotId(Long plotId);
}
