package com.farmSphere.auth.data.model;


import jakarta.persistence.Column;
import jakarta.persistence.Table;
import com.farmSphere.core.enums.REGISTRATION_STATUS;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@Table( name = "investors")
public class Investor extends User{
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue
    private Long investorId;
    @Column(nullable = false)
    private LocalDateTime startDate;
    private REGISTRATION_STATUS registrationStatus = REGISTRATION_STATUS.SUBMITTED;


}
