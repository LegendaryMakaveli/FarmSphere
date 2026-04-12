package com.farmSphere.auth.data.model;


import jakarta.persistence.*;
import lombok.Getter;
import com.farmSphere.core.enums.REGISTRATION_STATUS;
import lombok.Setter;


@Entity
@Setter
@Getter
@Table( name = "farmers")
public class Farmer{
    @Id
    private Long id;

    private String farmName;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    private String farmAddress;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EXPERIENCE experienceLevel;

    private float totalLandSize;
    private Long assignedPlotId;

    @Enumerated(EnumType.STRING)
    private REGISTRATION_STATUS registrationStatus;
}
