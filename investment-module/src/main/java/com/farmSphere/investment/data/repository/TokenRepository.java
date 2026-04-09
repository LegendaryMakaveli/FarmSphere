package com.farmSphere.investment.data.repository;

import com.farmSphere.investment.data.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByOwnerId(Long ownerId);
    List<Token> findAllByFarmAsset_Id(Long assetId);
    Optional<Token> findByFarmAsset_IdAndOwnerId(Long assetId, Long ownerId);
    List<Token> findAllByFarmAsset_IdAndRoiDistributedFalse(Long assetId);
}
