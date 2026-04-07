package com.farmSphere.marketplace.data.repository;

import com.farmSphere.marketplace.data.model.SaleConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaleConfirmationRepository extends JpaRepository<SaleConfirmation, Long> {
    Optional<SaleConfirmation> findByOrder_OrderId(Long orderId);
}
