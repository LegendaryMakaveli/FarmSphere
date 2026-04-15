package com.farmSphere.notification.listeners;


import com.farmSphere.core.event.tool.*;
import com.farmSphere.notification.channel.EmailChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ToolListener {
    private final EmailChannel emailChannel;

    @Value("${app.admin.email}")
    private String adminEmail;

    @EventListener
    public void onBookingCreated(ToolBookingCreatedEvent event) {
        emailChannel.send(
                adminEmail,
                "New Tool Booking Request - FarmSphere",
                "A new tool booking has been submitted.\n\n"
                        + "Booking ID : " + event.getBookingId() + "\n"
                        + "Tool       : " + event.getToolName() + "\n"
                        + "Quantity   : " + event.getQuantityRequested() + "\n"
                        + "Farmer ID  : " + event.getFarmerId() + "\n\n"
                        + "Please login to approve or reject."
        );
    }

    @EventListener
    public void onBookingApproved(ToolBookingApprovedEvent event) {
        emailChannel.send(
                event.getFarmerEmail(),
                "Tool Booking Approved - FarmSphere",
                "Your tool booking has been approved!\n\n"
                        + "Tool       : " + event.getToolName() + "\n"
                        + "Start Date : " + event.getStartDate() + "\n"
                        + "Booking ID : " + event.getBookingId() + "\n\n"
                        + "Please come in to pick up your tool on the start date."
        );
    }

    @EventListener
    public void onBookingRejected(ToolBookingRejectedEvent event) {
        emailChannel.send(
                event.getFarmerEmail(),
                "Tool Booking Update - FarmSphere",
                "Unfortunately your tool booking was not approved.\n\n"
                        + "Tool   : " + event.getToolName() + "\n"
                        + "Reason : " + event.getReason() + "\n\n"
                        + "Please contact admin for more information."
        );
    }

    @EventListener
    public void onToolPickedUp(ToolPickedUpEvent event) {
        log.info("Tool picked up — Booking: {} | Remaining qty: {}", event.getBookingId(), event.getRemainingQuantity());
    }

    @EventListener
    public void onToolReturned(ToolReturnedEvent event) {
        log.info("Tool returned — Booking: {} | Restored qty: {}", event.getBookingId(), event.getRestoredQuantity());
    }
}
