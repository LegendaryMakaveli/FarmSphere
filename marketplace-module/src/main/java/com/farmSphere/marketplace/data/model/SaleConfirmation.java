package com.farmSphere.marketplace.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "sale_confirmations")
public class SaleConfirmation {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long confirmationId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(nullable = false)
    private Long confirmedByAdminId;

    @Column(nullable = false)
    private LocalDateTime confirmationDate = LocalDateTime.now();

    private String notes;
}