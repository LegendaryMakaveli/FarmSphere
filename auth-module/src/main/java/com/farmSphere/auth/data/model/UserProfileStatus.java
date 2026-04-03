package com.farmSphere.auth.data.model;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileStatus {
    private boolean isFarmer;
    private FarmingStatus farmerStatus;

    private boolean isInvestor;
    private InvestmentStatus investorStatus;

}
