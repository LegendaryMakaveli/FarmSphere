package com.farmSphere.auth.service;

import com.farmSphere.auth.data.model.*;
import com.farmSphere.auth.data.repository.FarmerRepository;
import com.farmSphere.auth.data.repository.InvestorRepository;
import com.farmSphere.auth.data.repository.UserRepository;
import com.farmSphere.auth.dto.request.PasswordResetRequest;
import com.farmSphere.auth.dto.request.UpgradeToFarmerRequest;
import com.farmSphere.auth.dto.request.UserLoginRequest;
import com.farmSphere.auth.dto.request.UserRegisterRequest;
import com.farmSphere.auth.dto.response.UserLoginResponse;
import com.farmSphere.auth.dto.response.UserRegisterResponse;
import com.farmSphere.auth.util.Mapper;
import com.farmSphere.auth.util.PasswordHash;
import com.farmSphere.core.enums.REGISTRATION_STATUS;
import com.farmSphere.core.event.auth.*;
import com.farmSphere.infrastructure.eventbus.DomainEventPublisher;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.infrastructure.security.JwtService;
import com.farmSphere.infrastructure.security.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static com.farmSphere.auth.util.Validation.validateRequest;
import static com.farmSphere.auth.util.Validation.validateResetPasswordRequest;



@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {
    private final UserRepository userRepository;
    private final FarmerRepository farmerRepository;
    private final InvestorRepository investorRepository;
    private final JwtService jwtService;
    private final DomainEventPublisher eventPublisher;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public UserRegisterResponse register(UserRegisterRequest request) {
        validateRequest(request);
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {throw new DomainException("Email already exists!", 409);});
        User user = Mapper.mapToRegisterUser(request);
        user.setPassword(PasswordHash.hash(request.getPassword()));

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(user.getId(), user.getEmail(), savedUser.getRoles().stream().map(ROLE::name).collect(Collectors.toSet()), user.getFirstName(), user.getSecondName(), user.getPhoneNumber());

        eventPublisher.publish(new UserRegisteredEvent(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getSecondName()
        ));

        return UserRegisterResponse.builder()
                .message("Registration successful!")
                .token(token)
                .firstName(savedUser.getFirstName())
                .secondName(savedUser.getSecondName())
                .email(savedUser.getEmail())
                .phoneNumber(savedUser.getPhoneNumber())
                .dateOfRegistration(savedUser.getDateCreated() != null ? FORMATTER.format(savedUser.getDateCreated()): null)
                .address(savedUser.getAddress())
                .gender(savedUser.getGender().name())
                .dateCreated(savedUser.getDateCreated() != null ? FORMATTER.format(savedUser.getDateCreated()) : null)
                .build();
    }

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        if(request.getEmail() == null || request.getEmail().trim().isEmpty()) throw new DomainException("Email cannot be empty", 400);
        if(request.getPassword() == null || request.getPassword().trim().isEmpty()) throw new DomainException("Password cannot be empty", 400);


        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new DomainException("User not found", 404));
        if(!PasswordHash.checkPassword(request.getPassword(), user.getPassword())) throw new DomainException("Invalid credentials", 401);

        if (!user.isActive()) throw new DomainException("Account has been deactivated", 403);
        boolean isFarmer   = farmerRepository.existsById(user.getId());
        boolean isInvestor = investorRepository.existsById(user.getId());

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRoles().stream().map(ROLE::name).collect(Collectors.toSet()), user.getFirstName(), user.getSecondName(), user.getPhoneNumber());

        String rolesAsString = user.getRoles().stream()
                .map(ROLE::name)
                .collect(Collectors.joining(","));

        eventPublisher.publish(new UserLogginEvent(
                user.getId(),
                user.getEmail(),
                rolesAsString,
                LocalDateTime.now()
        ));

        return UserLoginResponse.builder()
                .message("Login successful!")
                .token(token)
                .roles(user.getRoles().stream().map(ROLE::name).collect(Collectors.toSet()))
                .email(user.getEmail())
                .userId(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .phoneNumber(user.getPhoneNumber())
                .lastLogin(user.getLastLogin() != null ? FORMATTER.format(user.getLastLogin()) : null)
                .profileStatus(UserProfileStatus.builder()
                        .isFarmer(isFarmer)
                        .isInvestor(isInvestor)
                        .build())
                .build();
    }


    @Transactional
    @Override
    public UserProfileStatus upgradeToFarmer(Long userId, String email, UpgradeToFarmerRequest request) {
        SecurityUtils.requireUser();
        User user = userRepository.findById(userId).orElseThrow(() -> new DomainException("User not found", 404));
        
        Farmer farmer = farmerRepository.findById(userId).orElse(new Farmer());
        
        if (farmer.getRegistrationStatus() != null && farmer.getRegistrationStatus() != REGISTRATION_STATUS.REJECTED) {
            throw new DomainException("You are already registered as a Farmer", 409);
        }

        farmer.setUser(user);
        farmer.setExperienceLevel(request.getExperienceLevel());
        farmer.setRegistrationStatus(REGISTRATION_STATUS.SUBMITTED);
        farmerRepository.save(farmer);
        user.getRoles().add(ROLE.FARMER);

        eventPublisher.publish(new FarmerRegisteredEvent(
                userId,
                user.getFirstName(),
                user.getEmail()
        ));

        return UserProfileStatus.builder()
                .isFarmer(true)
                .farmerStatus(REGISTRATION_STATUS.SUBMITTED)
                .isInvestor(investorRepository.existsById(userId))
                .investorStatus(investorRepository.findById(userId).map(Investor::getRegistrationStatus).orElse(null))
                .build();
    }

    @Override
    @Transactional
    public UserProfileStatus upgradeToInvestor(Long userId, String email) {
        SecurityUtils.requireUser();
        User user = userRepository.findById(userId).orElseThrow(() -> new DomainException("User not found", 404));

        Investor investor = investorRepository.findById(userId).orElse(new Investor());
        
        if (investor.getRegistrationStatus() != null && investor.getRegistrationStatus() != REGISTRATION_STATUS.REJECTED) {
            throw new DomainException("You are already registered as an Investor", 409);
        }

        investor.setUser(user);
        investor.setStartDate(LocalDateTime.now());
        investor.setRegistrationStatus(REGISTRATION_STATUS.SUBMITTED);
        investorRepository.save(investor);

        user.getRoles().add(ROLE.INVESTOR);

        eventPublisher.publish(new InvestorRegisteredEvent(
                userId,
                user.getFirstName(),
                user.getEmail()
        ));

        return UserProfileStatus.builder()
                .isFarmer(farmerRepository.existsById(userId))
                .farmerStatus(farmerRepository.findById(userId).map(Farmer::getRegistrationStatus).orElse(null))
                .isInvestor(true)
                .investorStatus(REGISTRATION_STATUS.SUBMITTED)
                .build();
    }

    @Override
    public UserProfileStatus getProfileStatus(Long userId) {
        boolean isFarmer   = farmerRepository.existsById(userId);
        boolean isInvestor = investorRepository.existsById(userId);

        REGISTRATION_STATUS farmerStatus   = null;
        REGISTRATION_STATUS investorStatus = null;

        if (isFarmer) {
            farmerStatus = farmerRepository.findById(userId)
                    .map(Farmer::getRegistrationStatus)
                    .orElse(null);
        }

        if (isInvestor) {
            investorStatus = investorRepository.findById(userId)
                    .map(Investor::getRegistrationStatus)
                    .orElse(null);
        }

        return UserProfileStatus.builder()
                .isFarmer(isFarmer)
                .farmerStatus(farmerStatus)
                .isInvestor(isInvestor)
                .investorStatus(investorStatus)
                .build();
    }


    public String resetPassword(PasswordResetRequest request) {
        validateResetPasswordRequest(request);
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new DomainException("User not found", 404));
        if(user.getPassword().equals(PasswordHash.hash(request.getNewPassword()))) throw new DomainException("New password cannot be the same as the old password", 400);
        user.setPassword(PasswordHash.hash(request.getNewPassword()));

        userRepository.save(user);
        eventPublisher.publish(new PasswordResetEvent(user.getId(), user.getEmail()));


        return "Password reset successful";
    }

}
