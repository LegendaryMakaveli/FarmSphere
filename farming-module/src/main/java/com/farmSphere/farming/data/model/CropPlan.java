package com.farmSphere.farming.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "crop_plans")
public class CropPlan {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cropPlanId;

    @Column(unique = true, nullable = false)
    private Long plotId;

    private boolean intercroppingEnabled = false;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}