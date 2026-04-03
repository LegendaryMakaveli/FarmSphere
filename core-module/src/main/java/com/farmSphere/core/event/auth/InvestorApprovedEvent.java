package com.farmSphere.core.event.auth;

public class InvestorApprovedEvent {
    private final Long userId;
    private final String email;
    private final String firstName;

    public InvestorApprovedEvent(Long userId, String email, String firstName) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
    }
    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
}