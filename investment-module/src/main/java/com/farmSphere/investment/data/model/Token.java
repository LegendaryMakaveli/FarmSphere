package com.farmSphere.investment.data.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "tokens")
public class Token {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private FarmAsset farmAsset;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String ownerEmail;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private int unitsOwned;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal purchasePricePerUnit;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalInvested;   // unitsOwned × purchasePricePerUnit

    @Column(nullable = false)
    private LocalDateTime purchaseDate = LocalDateTime.now();

    private boolean roiDistributed = false;
}
