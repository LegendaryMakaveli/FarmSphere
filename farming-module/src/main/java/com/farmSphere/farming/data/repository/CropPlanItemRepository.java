package com.farmSphere.farming.data.repository;

import com.farmSphere.farming.data.model.CropPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CropPlanItemRepository extends JpaRepository<CropPlanItem, Long> {
}
