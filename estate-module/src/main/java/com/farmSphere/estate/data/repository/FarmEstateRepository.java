package com.farmSphere.estate.data.repository;

import com.farmSphere.estate.data.model.FarmEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmEstateRepository extends JpaRepository<FarmEstate, Long> {
    Optional<FarmEstate> findByName(String name);
}
