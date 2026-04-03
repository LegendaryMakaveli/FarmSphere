package com.farmSphere.auth.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@Getter
@Table( name = "users")
public class User {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String secondName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GENDER gender;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    private String profilePicture;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ROLE role = ROLE.USER;

    private boolean active = false;
    private boolean isVerified = false;
    private String dateCreated;
    private String lastLogin;
}
