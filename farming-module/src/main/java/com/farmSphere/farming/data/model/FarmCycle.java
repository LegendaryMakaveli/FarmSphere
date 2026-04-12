package com.farmSphere.farming.data.model;

import com.farmSphere.core.enums.FARMING_STATUS;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "farm_cycles")
public class FarmCycle {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long farmCycleId;

    @Column(nullable = false)
    private Long plotId;

    @Column(nullable = false)
    private Long cropPlanId;

    @Column(nullable = false)
    private Long farmerId;

    @Column(nullable = false)
    private String farmerEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FARMING_STATUS status = FARMING_STATUS.PLANNED;

    @Column(nullable = false)
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}