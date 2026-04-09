package com.farmSphere.investment.data.repository;

import com.farmSphere.investment.data.model.YieldDistribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface YieldDistributionRepository extends JpaRepository<YieldDistribution, Long> {
    Optional<YieldDistribution> findByFarmAsset_Id(Long assetId);
    boolean existsByFarmAsset_Id(Long assetId);
}
