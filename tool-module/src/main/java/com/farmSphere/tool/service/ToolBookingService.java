package com.farmSphere.tool.service;

import com.farmSphere.core.enums.BOOKING_STATUS;
import com.farmSphere.tool.dto.request.ToolBookingRequest;
import com.farmSphere.tool.dto.response.ToolBookingResponse;

import java.util.List;

public interface ToolBookingService {
    ToolBookingResponse createBooking(Long farmerId, String farmerEmail, String farmerFirstName, String farmerLastName, String farmerPhone, ToolBookingRequest request);
    ToolBookingResponse approveBooking(Long bookingId, Long adminId);
    ToolBookingResponse rejectBooking(Long bookingId, Long adminId, String reason);
    ToolBookingResponse pickupTool(Long bookingId);
    ToolBookingResponse returnTool(Long bookingId);
    List<ToolBookingResponse> getMyBookings(Long farmerId);
    List<ToolBookingResponse> getBookingsByStatus(BOOKING_STATUS status);
    List<ToolBookingResponse> getAllBookings();

}
