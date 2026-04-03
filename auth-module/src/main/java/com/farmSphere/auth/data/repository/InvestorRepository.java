package com.farmSphere.auth.data.repository;

import com.farmSphere.auth.data.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;
import com.farmSphere.core.enums.REGISTRATION_STATUS;

import java.util.List;
import java.util.Optional;

public interface InvestorRepository extends JpaRepository<Investor, Long> {
    Optional<Investor> findById(Long id);
    Optional<Investor> findByEmail(String Email);
    boolean existsById(Long id);
    List<Investor> findAllByRegistrationStatus(REGISTRATION_STATUS status);
}

