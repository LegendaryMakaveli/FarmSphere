package com.farmSphere.marketplace.data.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

public class OrderItem {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produce_id", nullable = false)
    private Produce produce;

    @Column(nullable = false)
    private float quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPriceAtOrder;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    private Long matchedByAdminId;
}
