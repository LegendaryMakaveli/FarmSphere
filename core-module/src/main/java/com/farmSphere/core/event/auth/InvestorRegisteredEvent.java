package com.farmSphere.core.event.auth;

public class InvestorRegisteredEvent {
    private final Long userId;
    private final String firstName;
    private final String email;

    public InvestorRegisteredEvent(Long userId, String firstName, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.email = email;
    }

    public Long getUserId() { return userId; }
    public String getFirstName() { return firstName; }
    public String getEmail() { return email; }
}
