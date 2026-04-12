package com.farmSphere.farming.data.model;

import com.farmSphere.core.enums.TASK_STATUS;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "tasks")
public class Task {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false)
    private Long farmCycleId;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private Long assignedFarmerId;

    @Column(nullable = false)
    private String assignedFarmerEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TASK_STATUS status = TASK_STATUS.PENDING;
}