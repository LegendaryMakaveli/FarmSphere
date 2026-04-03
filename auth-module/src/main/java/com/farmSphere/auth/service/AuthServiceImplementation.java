package com.farmSphere.auth.service;

import com.farmSphere.auth.data.model.User;
import com.farmSphere.auth.data.repository.UserRepository;
import com.farmSphere.auth.dto.request.PasswordResetRequest;
import com.farmSphere.auth.dto.request.UserLoginRequest;
import com.farmSphere.auth.dto.request.UserRegisterRequest;
import com.farmSphere.auth.dto.response.UserLoginResponse;
import com.farmSphere.auth.dto.response.UserRegisterResponse;
import com.farmSphere.auth.util.Mapper;
import com.farmSphere.auth.util.PasswordHash;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

import static com.farmSphere.auth.util.Validation.validateRequest;
import static com.farmSphere.auth.util.Validation.validateResetPasswordRequest;


@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public UserRegisterResponse registerFarmer(UserRegisterRequest request) {
        validateRequest(request);
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {throw new DomainException("Email already exists!", 409);});
        User user = Mapper.mapToRegisterUser(request);
        user.setPassword(PasswordHash.hash(request.getPassword()));

        User savedBuyer = userRepository.save(user);

        return UserRegisterResponse.builder()
                .message("Registration successful!")
                .firstName(savedBuyer.getFirstName())
                .secondName(savedBuyer.getSecondName())
                .email(savedBuyer.getEmail())
                .phoneNumber(savedBuyer.getPhoneNumber())
                .dateOfRegistration(savedBuyer.getDateCreated())
                .address(savedBuyer.getAddress())
                .gender(savedBuyer.getGender().name())
                .build();
    }

    @Override
    public UserLoginResponse loginFarmer(UserLoginRequest request) {
        if(request.getEmail() == null || request.getEmail().trim().isEmpty()) throw new DomainException("Email cannot be empty", 400);
        if(request.getPassword() == null || request.getPassword().trim().isEmpty()) throw new DomainException("Password cannot be empty", 400);
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new DomainException("User not found", 404));
        if(!PasswordHash.checkPassword(request.getPassword(), user.getPassword())) throw new DomainException("Invalid credentials", 401);

        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        user.setActive(true);
        user.setLastLogin(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(java.time.LocalDateTime.now()));

        return UserLoginResponse.builder()
                .message("Login successful!")
                .token(token)
                .role(user.getRole())
                .email(user.getEmail())
                .userId(user.getId())
                .lastLogin(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(java.time.LocalDateTime.now()))
                .build();
    }

    public String resetPassword(PasswordResetRequest request) {
        validateResetPasswordRequest(request);
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new DomainException("User not found", 404));
        user.setPassword(PasswordHash.hash(request.getNewPassword()));

        userRepository.save(user);

        return "Password reset successful";
    }



}
