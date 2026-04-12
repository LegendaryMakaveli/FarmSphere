package com.farmSphere.farming.data.model;

import com.farmSphere.core.enums.PRODUCE_CATEGORY;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "crops")
public class Crop {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cropId;

    @Column(nullable = false, unique = true)
    private String cropName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PRODUCE_CATEGORY category;

    private int growthDurationDays;

    private String description;
}