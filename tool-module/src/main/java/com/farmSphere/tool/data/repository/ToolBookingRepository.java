package com.farmSphere.tool.data.repository;

import com.farmSphere.tool.data.model.ToolBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ToolBookingRepository extends JpaRepository<ToolBooking, Long> {
}
