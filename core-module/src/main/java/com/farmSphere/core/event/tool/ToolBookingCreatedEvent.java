package com.farmSphere.core.event.tool;

public class ToolBookingCreatedEvent {
    private final Long bookingId;
    private final Long farmerId;
    private final String farmerEmail;
    private final String farmerDetails;
    private final String toolName;
    private final int quantityRequested;

    public ToolBookingCreatedEvent(Long bookingId, Long farmerId,
                                   String farmerEmail, String farmerData, String toolName,
                                   int quantityRequested) {
        this.bookingId = bookingId;
        this.farmerId = farmerId;
        this.farmerEmail = farmerEmail;
        this.farmerDetails = farmerData;
        this.toolName = toolName;
        this.quantityRequested = quantityRequested;
    }
    public Long getBookingId() { return bookingId; }
    public Long getFarmerId() { return farmerId; }
    public String getFarmerEmail() { return farmerEmail; }
    public String getFarmerDetails() { return farmerDetails; }
    public String getToolName() { return toolName; }
    public int getQuantityRequested() { return quantityRequested; }
}
