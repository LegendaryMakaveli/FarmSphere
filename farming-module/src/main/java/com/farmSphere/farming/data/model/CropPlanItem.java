package com.farmSphere.farming.data.model;

import com.farmSphere.core.enums.CROP_ITEM_ROLE;
import com.farmSphere.core.enums.YEILD_UNIT;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "crop_plan_items")
public class CropPlanItem {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(nullable = false)
    private Long cropPlanId;

    @Column(nullable = false)
    private Long cropId;

    @Column(nullable = false)
    private String cropName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CROP_ITEM_ROLE role;

    private String spacingPattern;

    @Column(nullable = false)
    private float expectedYield;

    @Column(nullable = false)
    private YEILD_UNIT yieldUnit;

    private float actualYield = 0;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}