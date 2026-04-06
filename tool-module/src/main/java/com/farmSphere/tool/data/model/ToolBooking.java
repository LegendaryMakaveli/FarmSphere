package com.farmSphere.tool.data.model;


import com.farmSphere.core.enums.BOOKING_STATUS;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "tool_bookings")
public class ToolBooking {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(nullable = false)
    private Long farmerId;

    @Column(nullable = false)
    private String farmerEmail;

    @Column(nullable = false)
    private Long toolId;

    @Column(nullable = false)
    private String toolName;

    @Column(nullable = false)
    private String farmerFirstName;

    @Column(nullable = false)
    private String farmerLastName;

    @Column(nullable = false)
    private String farmerPhone;

    @Column(nullable = false)
    private int quantityRequested;

    @Column(nullable = false)
    private LocalDateTime bookingDate;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    private Long processedByAdminId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BOOKING_STATUS status = BOOKING_STATUS.PENDING;

    private String rejectionReason;

    private LocalDateTime createdAt = LocalDateTime.now();
}
