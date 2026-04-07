package com.farmSphere.marketplace.data.model;

import com.farmSphere.core.enums.ORDER_STATUS;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Setter
@Getter
@Table(name = "orders")
public class Order {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long buyerId;

    @Column(nullable = false)
    private String buyerEmail;

    @Column(nullable = false)
    private String buyerName;

    @Column(nullable = false)
    private String buyerPhone;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ORDER_STATUS status = ORDER_STATUS.PENDING;

    private Long matchedByAdminId;

    private String cancellationReason;

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;
}
