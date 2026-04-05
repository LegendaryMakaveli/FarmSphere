package com.farmSphere.estate.data.model;

import com.farmSphere.core.enums.PLOT_STATUS;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "plots")
public class Plot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plotId;

    @Column(nullable = false)
    private Long clusterId;

    @Column(nullable = false)
    private float plotSize;

    private String farmerEmail;

    private String location;

    @Column(nullable = false)
    private String soilType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PLOT_STATUS status = PLOT_STATUS.AVAILABLE;

    private Long assignedFarmerId;

    private LocalDateTime assignedDate;
}
