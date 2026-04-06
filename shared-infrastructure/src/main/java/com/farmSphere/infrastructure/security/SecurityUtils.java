package com.farmSphere.infrastructure.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;

public class SecurityUtils {

    @SuppressWarnings("unchecked")
    private static Map<String, Object> getDetails() {
        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken)
                        SecurityContextHolder.getContext().getAuthentication();
        return (Map<String, Object>) auth.getDetails();
    }

    public static Long getCurrentUserId() {
        return (Long) getDetails().get("userId");
    }

    public static String getCurrentUserRole() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();
    }

    public static String getCurrentUserEmail() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    public static String getCurrentUserFirstName() {
        return (String) getDetails().get("firstName");
    }

    public static String getCurrentUserLastName() {
        return (String) getDetails().get("lastName");
    }

    public static String getCurrentUserPhone() {
        return (String) getDetails().get("phone");
    }
}
