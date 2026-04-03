package com.farmSphere.auth.data.repository;

import com.farmSphere.auth.data.model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long>{
    Optional<Farmer> findById(String id);
    Optional<Farmer> findByEmail(String email);
    Optional<Farmer> findByPhoneNumber(String phoneNumber);
}
