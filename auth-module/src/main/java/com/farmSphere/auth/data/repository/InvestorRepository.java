package com.farmSphere.auth.data.repository;

import com.farmSphere.auth.data.model.Investor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvestorRepository extends JpaRepository<Investor, Long> {
    Optional<Investor> findById(String id);
    Optional<Investor> findByEmail(String Email);
}
