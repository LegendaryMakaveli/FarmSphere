package com.farmSphere.auth.data.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Table( name = "farmers")
public class Farmer extends User{
    @Column(nullable = false)
    private String farmName;

    @Column(nullable = false)
    private String farmAddress;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EXPERIENCE experienceLevel;

    private float totalLandSize;
    private Long assignedPlotId;

    @Enumerated(EnumType.STRING)
    private REGISTRATION_STATUS registrationStatus;
}
