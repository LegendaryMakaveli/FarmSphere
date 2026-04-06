package com.farmSphere.tool.util;

import com.farmSphere.core.enums.BOOKING_STATUS;
import com.farmSphere.tool.data.model.Tool;
import com.farmSphere.tool.data.model.ToolBooking;
import com.farmSphere.tool.dto.request.CreateToolRequest;
import com.farmSphere.tool.dto.request.ToolBookingRequest;

import java.time.LocalDateTime;

public class Validation {

    public static void validateTool(CreateToolRequest request) {
        if(request.getToolName() == null || request.getToolName().trim().isEmpty()) throw new IllegalArgumentException("Tool name cannot be empty");
        if(request.getQuantityAvailable() <= 0) throw new IllegalArgumentException("Quantity must be greater than 0");
        if(request.getConditionStatus() == null || request.getConditionStatus().trim().isEmpty()) throw new IllegalArgumentException("Condition status cannot be empty");
        if(request.getDescription() == null || request.getDescription().trim().isEmpty()) throw new IllegalArgumentException("Description cannot be empty");
    }

    public static ToolBooking mapToCreateBooking(Long farmerId, String farmerEmail, String farmerFirstName, String farmerLastName, String farmerPhone, ToolBookingRequest request, Tool tool) {
        ToolBooking booking = new ToolBooking();
        booking.setFarmerId(farmerId);
        booking.setFarmerEmail(farmerEmail);
        booking.setFarmerFirstName(farmerFirstName);
        booking.setFarmerLastName(farmerLastName);
        booking.setFarmerPhone(farmerPhone);
        booking.setToolId(tool.getToolId());
        booking.setToolName(request.getToolName());
        booking.setQuantityRequested(request.getQuantityRequested());
        booking.setBookingDate(LocalDateTime.now());
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setStatus(BOOKING_STATUS.PENDING);
        return booking;
    }

}
