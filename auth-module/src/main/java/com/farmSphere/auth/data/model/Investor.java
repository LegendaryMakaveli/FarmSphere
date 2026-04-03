package com.farmSphere.auth.data.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import com.farmSphere.core.enums.REGISTRATION_STATUS;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@Table( name = "investors")
public class Investor extends User{
    @Column(nullable = false)
    private LocalDateTime startDate;
    private REGISTRATION_STATUS registrationStatus = REGISTRATION_STATUS.SUBMITTED;


}
