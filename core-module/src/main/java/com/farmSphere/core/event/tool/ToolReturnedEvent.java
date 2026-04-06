package com.farmSphere.core.event.tool;

public class ToolReturnedEvent {
    private final Long bookingId;
    private final Long toolId;
    private final String toolName;
    private final int restoredQuantity;

    public ToolReturnedEvent(Long bookingId, Long toolId,
                             String toolName, int restoredQuantity) {
        this.bookingId = bookingId;
        this.toolId = toolId;
        this.toolName = toolName;
        this.restoredQuantity = restoredQuantity;
    }
    public Long getBookingId() { return bookingId; }
    public Long getToolId() { return toolId; }
    public String getToolName() { return toolName; }
    public int getRestoredQuantity() { return restoredQuantity; }
}
