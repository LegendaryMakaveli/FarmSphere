package com.farmSphere.core.event.auth;

public class FarmerApprovedEvent {
    private final Long userId;
    private final String email;
    private final String firstName;

    public FarmerApprovedEvent(Long userId, String email, String firstName) {
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
    }
    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
}
