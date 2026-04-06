package com.farmSphere.marketplace.data.model;


import com.farmSphere.core.enums.PRODUCE_CATEGORY;
import com.farmSphere.core.enums.PRODUCE_STATUS;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "produce")
public class Produce {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produceId;
    @Column(nullable = false)
    private Long farmerId;
    @Column(nullable = false)
    private String farmerName;
    @Column(nullable = false)
    private String farmerEmail;
    @Column(nullable = false)
    private String cropName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PRODUCE_CATEGORY category;

    @Column(nullable = false)
    private float quantityAvailable;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerUnit;

    @Column(nullable = false)
    private LocalDate harvestDate;

    @Column(nullable = false)
    private LocalDate expiryDate;

    private String description;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PRODUCE_STATUS status = PRODUCE_STATUS.AVAILABLE;

    @Column(nullable = false)
    private LocalDateTime listedAt = LocalDateTime.now();

    private LocalDateTime updatedAt;
}
