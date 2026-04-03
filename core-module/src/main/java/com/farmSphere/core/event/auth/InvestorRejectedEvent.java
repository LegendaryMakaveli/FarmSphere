package com.farmSphere.core.event.auth;

public class InvestorRejectedEvent {
    private final Long userId;
    private final String email;
    private final String firstName;
    private final String reason;

    public InvestorRejectedEvent(Long userId, String email, String firstName, String reason) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.reason = reason;
    }
    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getReason() { return reason; }
}
