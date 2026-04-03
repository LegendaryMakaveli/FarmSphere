package com.farmSphere.core.event.auth;

import java.time.LocalDateTime;

public class UserLogginEvent {
    private final Long userId;
    private final String email;
    private final String role;
    private final LocalDateTime loginTime;

    public UserLogginEvent(Long userId, String email, String role, LocalDateTime loginTime) {
        this.userId = userId;
        this.email = email;
        this.role = role;
        this.loginTime = loginTime;
    }

    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public LocalDateTime getLoginTime() { return loginTime; }
}
