package com.farmSphere.auth.data.model;


import jakarta.persistence.*;
import com.farmSphere.core.enums.REGISTRATION_STATUS;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@Table( name = "investors")
public class Investor {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(nullable = false)
    private LocalDateTime startDate;
    private REGISTRATION_STATUS registrationStatus = REGISTRATION_STATUS.SUBMITTED;


}
