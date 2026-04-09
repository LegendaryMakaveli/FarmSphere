package com.farmSphere.investment.data.model;


import com.farmSphere.core.enums.YIELD_STATUS;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "yield_distributions")
public class YieldDistribution {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long yieldId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false, unique = true)
    private FarmAsset farmAsset;

    @Column(nullable = false)
    private float actualROI;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPaidOut;

    @Column(nullable = false)
    private int totalInvestorsPaid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private YIELD_STATUS status = YIELD_STATUS.PENDING;

    @Column(nullable = false)
    private Long distributedByAdminId;

    private LocalDateTime distributedAt;
}
