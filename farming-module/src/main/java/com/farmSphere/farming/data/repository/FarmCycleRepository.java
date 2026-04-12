package com.farmSphere.farming.data.repository;

import com.farmSphere.farming.data.model.FarmCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FarmCycleRepository extends JpaRepository<FarmCycle, Long> {
}
