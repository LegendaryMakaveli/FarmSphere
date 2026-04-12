package com.farmSphere.auth.service;

import com.farmSphere.auth.data.model.Investor;
import com.farmSphere.auth.dto.response.UpgradeFarmerResponse;
import com.farmSphere.auth.dto.response.UpgradeInvestorResponse;

import java.util.List;

public interface AdminService {
    List<UpgradeFarmerResponse> getPendingFarmers();
    List<UpgradeInvestorResponse> getPendingInvestors();
    void approveFarmer(Long farmerId);
    void rejectFarmer(Long farmerId, String reason);
    void approveInvestor(Long investorId);
    void rejectInvestor(Long investorId, String reason);
}
