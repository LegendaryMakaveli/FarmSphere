package com.farmSphere.marketplace.data.repository;

import com.farmSphere.core.enums.ORDER_STATUS;
import com.farmSphere.marketplace.data.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByBuyerId(Long buyerId);
    List<Order> findAllByStatus(ORDER_STATUS status);
    Optional<Order> findByOrderIdAndBuyerId(Long orderId, Long buyerId);
}
