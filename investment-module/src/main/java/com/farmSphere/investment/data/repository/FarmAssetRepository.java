package com.farmSphere.investment.data.repository;


import com.farmSphere.core.enums.ASSET_STATUS;
import com.farmSphere.investment.data.model.FarmAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmAssetRepository extends JpaRepository<FarmAsset, Long> {
    boolean existsByCropPlanId(Long cropPlanId);
    List<FarmAsset> findAllByStatus(ASSET_STATUS status);
    Optional<FarmAsset> findByCropPlanId(Long cropPlanId);
}
