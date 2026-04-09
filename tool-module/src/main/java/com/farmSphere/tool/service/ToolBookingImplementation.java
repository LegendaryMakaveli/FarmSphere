package com.farmSphere.tool.service;


import com.farmSphere.core.enums.BOOKING_STATUS;
import com.farmSphere.core.event.tool.*;
import com.farmSphere.infrastructure.eventbus.DomainEventPublisher;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.infrastructure.security.SecurityUtils;
import com.farmSphere.tool.data.model.Tool;
import com.farmSphere.tool.data.model.ToolBooking;
import com.farmSphere.tool.data.repository.ToolBookingRepository;
import com.farmSphere.tool.data.repository.ToolRepository;
import com.farmSphere.tool.dto.request.ToolBookingRequest;
import com.farmSphere.tool.dto.response.ToolBookingResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.time.LocalDateTime;
import java.util.List;

import static com.farmSphere.tool.util.Validation.mapToCreateBooking;

@Service
@RequiredArgsConstructor
public class ToolBookingImplementation implements ToolBookingService{

    private final ToolBookingRepository bookingRepository;
    private final ToolRepository toolRepository;
    private final DomainEventPublisher eventPublisher;

    @Transactional
    @Override
    public ToolBookingResponse createBooking(Long farmerId, String farmerEmail, String farmerFirstName, String farmerLastName, String farmerPhone, ToolBookingRequest request) {
        SecurityUtils.requireFarmer();
        Tool tool = toolRepository.findById(request.getToolId()).orElseThrow(() -> new DomainException("Tool not found", 404));
        if (!tool.isAvailabilityStatus()) throw new DomainException("Tool is currently unavailable", 400);
        if (request.getQuantityRequested() > tool.getQuantityAvailable()) throw new DomainException("Only " + tool.getQuantityAvailable() + " unit(s) available", 400);
        if (!request.getEndDate().isAfter(request.getStartDate())) throw new DomainException("End date must be after start date", 400);

        ToolBooking booking = mapToCreateBooking(farmerId, farmerEmail, farmerFirstName, farmerLastName, farmerPhone, request, tool);

        ToolBooking saved = bookingRepository.save(booking);

        eventPublisher.publish(new ToolBookingCreatedEvent(
                saved.getBookingId(),
                farmerId,
                farmerEmail,
                farmerFirstName + " " + farmerLastName + " " + farmerPhone,
                request.getToolName(),
                request.getQuantityRequested()
        ));

        return ToolBookingResponse.from(saved);    }

    @Transactional
    @Override
    public ToolBookingResponse approveBooking(Long bookingId, Long adminId) {
        SecurityUtils.requireAdmin();
        ToolBooking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new DomainException("Booking not found", 404));
        if (booking.getStatus() != BOOKING_STATUS.PENDING) throw new DomainException("Only pending bookings can be approved", 400);

        booking.setStatus(BOOKING_STATUS.APPROVED);
        booking.setProcessedByAdminId(adminId);
        ToolBooking saved = bookingRepository.save(booking);

        eventPublisher.publish(new ToolBookingApprovedEvent(
                saved.getBookingId(),
                saved.getFarmerId(),
                saved.getFarmerEmail(),
                saved.getToolName(),
                saved.getStartDate()
        ));

        return ToolBookingResponse.from(saved);
    }

    @Transactional
    @Override
    public ToolBookingResponse rejectBooking(Long bookingId, Long adminId, String reason) {
        SecurityUtils.requireAdmin();
        ToolBooking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new DomainException("Booking not found", 404));
        if (booking.getStatus() != BOOKING_STATUS.PENDING) throw new DomainException("Only pending bookings can be rejected", 400);

        booking.setStatus(BOOKING_STATUS.REJECTED);
        booking.setProcessedByAdminId(adminId);
        booking.setRejectionReason(reason);
        ToolBooking saved = bookingRepository.save(booking);

        eventPublisher.publish(new ToolBookingRejectedEvent(
                saved.getBookingId(),
                saved.getFarmerId(),
                saved.getFarmerEmail(),
                saved.getToolName(),
                reason
        ));

        return ToolBookingResponse.from(saved);

    }

    @Transactional
    @Override
    public ToolBookingResponse pickupTool(Long bookingId) {
        SecurityUtils.requireAdmin();
        ToolBooking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new DomainException("Booking not found", 404));
        if (booking.getStatus() != BOOKING_STATUS.APPROVED) throw new DomainException("Booking must be approved before pickup", 400);
        Tool tool = booking.getToolId() != null ? toolRepository.findById(booking.getToolId()).orElseThrow(() -> new DomainException("Tool not found", 404)) : null;

        int newQty = tool.getQuantityAvailable() - booking.getQuantityRequested();
        if (newQty < 0) throw new DomainException("Not enough tools in stock", 400);

        tool.setQuantityAvailable(newQty);
        tool.setAvailabilityStatus(newQty > 0);
        toolRepository.save(tool);

        booking.setStatus(BOOKING_STATUS.IN_USE);
        ToolBooking saved = bookingRepository.save(booking);

        eventPublisher.publish(new ToolPickedUpEvent(
                saved.getBookingId(),
                tool.getToolId(),
                tool.getToolName(),
                newQty
        ));

        return ToolBookingResponse.from(saved);    }

    @Transactional
    @Override
    public ToolBookingResponse returnTool(Long bookingId) {
        SecurityUtils.requireAdmin();
        ToolBooking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new DomainException("Booking not found", 404));
        if (booking.getStatus() != BOOKING_STATUS.IN_USE) throw new DomainException("Only IN_USE tools can be returned", 400);

        Tool tool = booking.getToolId() != null ? toolRepository.findById(booking.getToolId()).orElseThrow(() -> new DomainException("Tool not found", 404)) : null;

        int newQty = tool.getQuantityAvailable() + booking.getQuantityRequested();
        tool.setQuantityAvailable(newQty);
        tool.setAvailabilityStatus(true);
        toolRepository.save(tool);

        booking.setStatus(BOOKING_STATUS.RETURNED);
        ToolBooking saved = bookingRepository.save(booking);

        eventPublisher.publish(new ToolReturnedEvent(
                saved.getBookingId(),
                tool.getToolId(),
                tool.getToolName(),
                newQty
        ));

        return ToolBookingResponse.from(saved);
    }

    @Override
    public List<ToolBookingResponse> getMyBookings(Long farmerId) {
        SecurityUtils.requireFarmer();
        return bookingRepository.findAllByFarmerId(farmerId)
                .stream()
                .map(ToolBookingResponse::from)
                .toList();
    }

    @Override
    public List<ToolBookingResponse> getBookingsByStatus(BOOKING_STATUS status) {
        SecurityUtils.requireAdmin();
        return bookingRepository.findAllByStatus(status)
                .stream()
                .map(ToolBookingResponse::from)
                .toList();
    }

    @Override
    public List<ToolBookingResponse> getAllBookings() {
        SecurityUtils.requireAdmin();
        return bookingRepository.findAll()
                .stream()
                .map(ToolBookingResponse::from)
                .toList();
    }
}
