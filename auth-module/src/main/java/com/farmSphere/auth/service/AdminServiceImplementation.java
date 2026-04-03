package com.farmSphere.auth.service;

import com.farmSphere.auth.data.model.Farmer;
import com.farmSphere.auth.data.model.Investor;
import com.farmSphere.auth.data.repository.FarmerRepository;
import com.farmSphere.auth.data.repository.InvestorRepository;
import com.farmSphere.core.enums.REGISTRATION_STATUS;
import com.farmSphere.core.event.auth.FarmerApprovedEvent;
import com.farmSphere.core.event.auth.FarmerRejectedEvent;
import com.farmSphere.core.event.auth.InvestorApprovedEvent;
import com.farmSphere.core.event.auth.InvestorRejectedEvent;
import com.farmSphere.infrastructure.eventbus.DomainEventPublisher;
import com.farmSphere.infrastructure.exception.DomainException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminServiceImplementation implements AdminService{
    private final FarmerRepository farmerRepository;
    private final InvestorRepository investorRepository;
    private final DomainEventPublisher eventPublisher;

    @Override
    public List<Farmer> getPendingFarmers() {
        return farmerRepository.findAllByRegistrationStatus(REGISTRATION_STATUS.SUBMITTED);
    }

    @Override
    public List<Investor> getPendingInvestors() {
        return investorRepository.findAllByRegistrationStatus(REGISTRATION_STATUS.SUBMITTED);
    }

    @Transactional
    @Override
    public void approveFarmer(Long farmerId) {
        Farmer farmer = farmerRepository.findById(farmerId).orElseThrow(() -> new DomainException("Farmer not found", 404));
        if (farmer.getRegistrationStatus() == REGISTRATION_STATUS.APPROVED) throw new DomainException("Farmer is already approved", 409);

        farmer.setRegistrationStatus(REGISTRATION_STATUS.APPROVED);
        farmerRepository.save(farmer);

        eventPublisher.publish(new FarmerApprovedEvent(
                farmer.getId(),
                farmer.getEmail(),
                farmer.getFirstName()
        ));
    }

    @Transactional
    @Override
    public void rejectFarmer(Long farmerId, String reason) {
        Farmer farmer = farmerRepository.findById(farmerId).orElseThrow(() -> new DomainException("Farmer not found", 404));

        farmer.setRegistrationStatus(REGISTRATION_STATUS.REJECTED);
        farmerRepository.save(farmer);

        eventPublisher.publish(new FarmerRejectedEvent(
                farmer.getId(),
                farmer.getEmail(),
                farmer.getFirstName(),
                reason
        ));
    }

    @Transactional
    @Override
    public void approveInvestor(Long investorId) {
        Investor investor = investorRepository.findById(investorId).orElseThrow(() -> new DomainException("Investor not found", 404));
        if (investor.getRegistrationStatus() == REGISTRATION_STATUS.APPROVED) throw new DomainException("Investor is already approved", 409);

        investor.setRegistrationStatus(REGISTRATION_STATUS.APPROVED);
        investorRepository.save(investor);

        eventPublisher.publish(new InvestorApprovedEvent(
                investor.getId(),
                investor.getEmail(),
                investor.getFirstName()
        ));
    }

    @Transactional
    @Override
    public void rejectInvestor(Long investorId, String reason) {
        Investor investor = investorRepository.findById(investorId). orElseThrow(() -> new DomainException("Investor not found", 404));

        investor.setRegistrationStatus(REGISTRATION_STATUS.REJECTED);
        investorRepository.save(investor);

        eventPublisher.publish(new InvestorRejectedEvent(
                investor.getId(),
                investor.getEmail(),
                investor.getFirstName(),
                reason
        ));
    }
}
