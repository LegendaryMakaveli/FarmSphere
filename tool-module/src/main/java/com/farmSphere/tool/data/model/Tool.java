package com.farmSphere.tool.data.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "tools")
public class Tool {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toolId;

    @Column(nullable = false, unique = true)
    private String toolName;

    private String description;

    @Column(nullable = false)
    private int quantityAvailable;

    @Column(nullable = false)
    private String conditionStatus;

    @Column(nullable = false)
    private boolean availabilityStatus = true;
}
