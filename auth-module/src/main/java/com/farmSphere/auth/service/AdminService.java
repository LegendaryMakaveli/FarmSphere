package com.farmSphere.auth.service;

import com.farmSphere.auth.data.model.Farmer;
import com.farmSphere.auth.data.model.Investor;

import java.util.List;

public interface AdminService {
    List<Farmer> getPendingFarmers();
    List<Investor> getPendingInvestors();
    void approveFarmer(Long farmerId);
    void rejectFarmer(Long farmerId, String reason);
    void approveInvestor(Long investorId);
    void rejectInvestor(Long investorId, String reason);
}
