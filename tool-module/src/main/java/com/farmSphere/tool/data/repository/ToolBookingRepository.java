package com.farmSphere.tool.data.repository;

import com.farmSphere.core.enums.BOOKING_STATUS;
import com.farmSphere.tool.data.model.ToolBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ToolBookingRepository extends JpaRepository<ToolBooking, Long> {
    List<ToolBooking> findAllByFarmerId(Long farmerId);
    List<ToolBooking> findAllByStatus(BOOKING_STATUS status);
    List<ToolBooking> findAllByToolId(Long toolId);
}
