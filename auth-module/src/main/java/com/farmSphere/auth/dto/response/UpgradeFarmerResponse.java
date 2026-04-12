package com.farmSphere.auth.dto.response;

import com.farmSphere.core.enums.REGISTRATION_STATUS;

public record UpgradeFarmerResponse (
    Long id,
    String farmName,
    String firstName,
    String secondName,
    int age,
    REGISTRATION_STATUS status

){

    public UpgradeFarmerResponse(Long id, String farmName, String firstName, String secondName, int age, REGISTRATION_STATUS status) {
        this.id = id;
        this.farmName = farmName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.status = status;
    }
}
