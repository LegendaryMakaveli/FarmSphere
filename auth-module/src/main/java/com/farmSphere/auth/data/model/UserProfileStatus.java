package com.farmSphere.auth.data.model;


import com.farmSphere.core.enums.REGISTRATION_STATUS;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileStatus {
    private boolean isFarmer;
    private REGISTRATION_STATUS farmerStatus;

    private boolean isInvestor;
    private REGISTRATION_STATUS investorStatus;

}
