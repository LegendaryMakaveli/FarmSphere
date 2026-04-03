package com.farmSphere.core.event.auth;

public class PasswordResetEvent {
    private final Long userId;
    private final String email;

    public PasswordResetEvent(Long userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
}
