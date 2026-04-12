package com.farmSphere.auth.dto.response;

import com.farmSphere.core.enums.REGISTRATION_STATUS;

public record UpgradeInvestorResponse (
    Long id,
    String firstName,
    String secondName,
    int age,
    REGISTRATION_STATUS status
){

    public UpgradeInvestorResponse(Long id, String firstName, String secondName, int age, REGISTRATION_STATUS status) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
        this.status = status;
    }
}
