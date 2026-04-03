package com.farmSphere.auth.data.model;


import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Table( name = "investors")
public class Investor extends User{
    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue
    private Long investorId;

}
