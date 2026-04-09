package com.farmSphere.investment.data.repository;

import com.farmSphere.investment.data.model.InvestmentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InvestmentTransactionRepository extends JpaRepository<InvestmentTransaction, Long> {
    List<InvestmentTransaction> findAllByUserId(Long userId);
    List<InvestmentTransaction> findAllByAssetId(Long assetId);
}
