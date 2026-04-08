package com.farmSphere.investment.data.model;


import com.farmSphere.core.enums.ASSET_STATUS;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "farm_assets")
public class FarmAsset {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long cropPlanId;

    @Column(nullable = false)
    private String cropName;

    @Column(nullable = false)
    private String farmerName;

    @Column(nullable = false)
    private int totalUnits;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private float expectedROI;

    private float actualROI;

    @Column(nullable = false)
    private int unitsSold = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ASSET_STATUS status = ASSET_STATUS.OPEN;

    @Column(nullable = false)
    private LocalDate fundingDeadline;  // after this date → CLOSED regardless

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;







//    helpers
    public int getRemainingUnits() {
        return totalUnits - unitsSold;
    }

    public boolean isFullyFunded() {
        return unitsSold >= totalUnits;
    }
}
