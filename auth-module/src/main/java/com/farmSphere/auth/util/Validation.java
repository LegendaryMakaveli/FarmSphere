package com.farmSphere.auth.util;

import com.farmSphere.auth.dto.request.PasswordResetRequest;
import com.farmSphere.auth.dto.request.UserRegisterRequest;
import com.farmSphere.infrastructure.exception.DomainException;

public class Validation {

    public static void validateRequest(UserRegisterRequest request) {
        String emailPatterns = "^[A-Za-z0-9._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$";
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,15}$";
        String namePattern = "^[A-Za-z]{2,30}$";
        if (!request.getEmail().matches(emailPatterns)) {throw new DomainException("Invalid email format!", 400);}
        if (!request.getPassword().matches(passwordPattern)) {throw new DomainException("Password must contain at least 6 characters, one uppercase letter, one lowercase letter, one number, and one special character!", 400);}
        if (!request.getFirstName().matches(namePattern)) {throw new DomainException("First name must contain only letters!", 400);}
        if (!request.getSecondName().matches(namePattern)) {throw new DomainException("Second name must contain only letters!", 400);}
        if (!request.getPhoneNumber().matches("^[0-9]{10}$")) {throw new DomainException("Invalid phone number format!", 400);}
        if (request.getAddress() == null || request.getAddress().trim().isEmpty() ) {throw new DomainException("Address cannot be empty", 400);}
        if (request.getGender() == null) {throw new DomainException("Gender cannot be empty", 400);}
        if (request.getAge() < 18) {throw new DomainException("User must be at least 18 years old!", 400);}
    }
    public static void validateResetPasswordRequest(PasswordResetRequest request) {
        String emailPatterns = "^[A-Za-z0-9._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,}$";
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,15}$";
        if(!request.getEmail().matches(emailPatterns)) throw new DomainException("Invalid email format!", 400);
        if(!request.getNewPassword().matches(passwordPattern)) throw new DomainException("Password must contain at least 6 characters, one uppercase letter, one lowercase letter, one number, and one special character!", 400);
    }

}
