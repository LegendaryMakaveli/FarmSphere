package com.farmSphere.core.event.tool;

public class ToolBookingRejectedEvent {
    private final Long bookingId;
    private final Long farmerId;
    private final String farmerEmail;
    private final String toolName;
    private final String reason;

    public ToolBookingRejectedEvent(Long bookingId, Long farmerId,
                                    String farmerEmail, String toolName,
                                    String reason) {
        this.bookingId = bookingId;
        this.farmerId = farmerId;
        this.farmerEmail = farmerEmail;
        this.toolName = toolName;
        this.reason = reason;
    }
    public Long getBookingId() { return bookingId; }
    public Long getFarmerId() { return farmerId; }
    public String getFarmerEmail() { return farmerEmail; }
    public String getToolName() { return toolName; }
    public String getReason() { return reason; }
}
