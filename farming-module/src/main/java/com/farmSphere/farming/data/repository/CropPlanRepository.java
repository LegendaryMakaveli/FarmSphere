package com.farmSphere.farming.data.repository;

import com.farmSphere.farming.data.model.CropPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CropPlanRepository extends JpaRepository<CropPlan, Long> {
}
