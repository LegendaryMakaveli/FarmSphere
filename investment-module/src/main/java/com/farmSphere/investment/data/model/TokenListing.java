package com.farmSphere.investment.data.model;


import com.farmSphere.core.enums.LISTING_STATUS;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "token_listings")
public class TokenListing {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id", nullable = false)
    private Token token;

    @Column(nullable = false)
    private Long sellerId;

    @Column(nullable = false)
    private String sellerEmail;

    @Column(nullable = false)
    private String sellerName;

    @Column(nullable = false)
    private int unitsToSell;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal askingPricePerUnit;

    private Long buyerId;
    private String buyerEmail;
    private String buyerName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LISTING_STATUS status = LISTING_STATUS.OPEN;

    @Column(nullable = false)
    private LocalDateTime listedAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime soldAt;

}
