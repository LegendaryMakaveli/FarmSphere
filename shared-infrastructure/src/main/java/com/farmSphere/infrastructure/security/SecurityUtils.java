package com.farmSphere.infrastructure.security;

import com.farmSphere.infrastructure.exception.DomainException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @SuppressWarnings("unchecked")
    public static Set<String> getCurrentUserRoles() {return (Set<String>) getDetails().get("roles");}

    public static boolean hasRole(String role) {return getCurrentUserRoles().contains(role);}

    public static String getCurrentUserEmail() {return SecurityContextHolder.getContext().getAuthentication().getName();}

    public static String getCurrentUserFirstName() {
        return (String) getDetails().get("firstName");
    }

    public static String getCurrentUserLastName() {
        return (String) getDetails().get("lastName");
    }

    public static String getCurrentUserPhone() {
        return (String) getDetails().get("phone");
    }


    public static boolean isUser() {return hasRole("USER");}

    public static boolean isFarmer() {return hasRole("FARMER");}

    public static boolean isInvestor() {return hasRole("INVESTOR");}

    public static boolean isAdmin() {return hasRole("ADMIN");}


    public static void requireRole(String role) {
        if (!hasRole(role)) throw new DomainException("Access denied — requires " + role + " role", 403);}

    public static void requireFarmer() { requireRole("FARMER"); }
    public static void requireInvestor() { requireRole("INVESTOR"); }
    public static void requireAdmin() { requireRole("ADMIN"); }
    public static void requireUser() { requireRole("USER"); }
}
