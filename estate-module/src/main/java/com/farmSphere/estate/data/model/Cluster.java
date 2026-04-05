package com.farmSphere.estate.data.model;

import com.farmSphere.core.enums.FARMING_MODEL;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@Table(name = "clusters")
public class Cluster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clusterId;

    private String clusterName;

    @Column(nullable = false)
    private Long estateId;

    @Column(nullable = false)
    private Long primaryCropId;

    @Column(nullable = false)
    private String primaryCropName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FARMING_MODEL farmingModel;
    private String description;
    private LocalDateTime createdAt;

}
