package com.farmSphere.tool.dto.response;

import com.farmSphere.tool.data.model.ToolBooking;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;


@Data
@Builder
public class ToolBookingResponse {
    private Long bookingId;
    private Long farmerId;
    private String farmerEmail;
    private String farmerName;
    private String farmerPhone;

    private Long toolId;
    private String toolName;
    private int quantityRequested;
    private String startDate;
    private String endDate;
    private String bookingDate;
    private String status;
    private String rejectionReason;
    private String createdAt;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ToolBookingResponse from(ToolBooking booking) {
        return ToolBookingResponse.builder()
                .bookingId(booking.getBookingId())
                .farmerId(booking.getFarmerId())
                .farmerEmail(booking.getFarmerEmail())
                .farmerName(booking.getFarmerFirstName() + " " + booking.getFarmerLastName())
                .farmerPhone(booking.getFarmerPhone())
                .toolId(booking.getToolId())
                .toolName(booking.getToolName())
                .quantityRequested(booking.getQuantityRequested())
                .startDate(DATE_FORMATTER.format(booking.getStartDate()))
                .endDate(DATE_FORMATTER.format(booking.getEndDate()))
                .bookingDate(DATE_FORMATTER.format(booking.getBookingDate()))
                .status(booking.getStatus().name())
                .rejectionReason(booking.getRejectionReason())
                .createdAt(FORMATTER.format(booking.getCreatedAt()))
                .build();
    }
}
