package com.farmSphere.farming.data.repository;

import com.farmSphere.core.enums.CROP_ITEM_ROLE;
import com.farmSphere.farming.data.model.CropPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CropPlanItemRepository extends JpaRepository<CropPlanItem, Long> {
    List<CropPlanItem> findAllByCropPlanId(Long cropPlanId);
    List<CropPlanItem> findAllByCropPlanIdAndRole(Long cropPlanId, CROP_ITEM_ROLE role);
    Optional<CropPlanItem> findByCropPlanIdAndRole(Long cropPlanId, CROP_ITEM_ROLE role);
    boolean existsByCropPlanIdAndRole(Long cropPlanId, CROP_ITEM_ROLE role);
}
