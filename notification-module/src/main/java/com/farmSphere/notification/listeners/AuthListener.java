package com.farmSphere.notification.listeners;


import com.farmSphere.core.event.auth.*;
import com.farmSphere.notification.channel.EmailChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class AuthListener {
    private final EmailChannel emailChannel;

    @EventListener
    public void onUserRegistered(UserRegisteredEvent event) {
        emailChannel.send(
                event.getEmail(),
                "Welcome to FarmSphere!",
                "Hi " + event.getFirstName() + ",\n\n"
                        + "Your FarmSphere account has been created successfully.\n"
                        + "You can now browse produce, place orders and upgrade your account to a Farmer or Investor.\n\n"
                        + "Welcome aboard!"
        );
    }

    @EventListener
    public void onUserLoggedIn(UserLogginEvent event) {
        emailChannel.send(
                event.getEmail(),
                "New Login Alert - FarmSphere",
                "Hi,\n\n"
                        + "A new login was detected on your FarmSphere account.\n"
                        + "Role: " + event.getRole() + "\n"
                        + "Time: " + event.getLoginTime() + "\n\n"
                        + "If this was not you, please reset your password immediately."
        );
    }

    @EventListener
    public void onPasswordReset(PasswordResetEvent event) {
        emailChannel.send(
                event.getEmail(),
                "Password Changed - FarmSphere",
                "Hi,\n\n"
                        + "Your FarmSphere password was just changed successfully.\n"
                        + "If you did not make this change, contact support immediately.\n\n"
                        + "Stay safe!"
        );
    }

    @EventListener
    public void onFarmerApproved(FarmerApprovedEvent event) {
        emailChannel.send(
                event.getEmail(),
                "Farming Account Approved - FarmSphere",
                "Hi " + event.getFirstName() + ",\n\n"
                        + "Great news! Your farming account has been approved.\n"
                        + "You can now access your Farming Dashboard and start managing your farm.\n\n"
                        + "Welcome to the FarmSphere farming community!"
        );
    }

    @EventListener
    public void onFarmerRejected(FarmerRejectedEvent event) {
        emailChannel.send(
                event.getEmail(),
                "Farming Account Update - FarmSphere",
                "Hi " + event.getFirstName() + ",\n\n"
                        + "Unfortunately your farming account request was not approved.\n"
                        + "Reason: " + event.getReason() + "\n\n"
                        + "Please contact support if you have any questions."
        );
    }

    @EventListener
    public void onInvestorApproved(InvestorApprovedEvent event) {
        emailChannel.send(
                event.getEmail(),
                "Investor Account Approved - FarmSphere",
                "Hi " + event.getFirstName() + ",\n\n"
                        + "Your investor account has been approved.\n"
                        + "You can now access your Investment Dashboard and start investing.\n\n"
                        + "Welcome to FarmSphere investments!"
        );
    }

    @EventListener
    public void onInvestorRejected(InvestorRejectedEvent event) {
        emailChannel.send(
                event.getEmail(),
                "Investor Account Update - FarmSphere",
                "Hi " + event.getFirstName() + ",\n\n"
                        + "Unfortunately your investor account request was not approved.\n"
                        + "Reason: " + event.getReason() + "\n\n"
                        + "Please contact support if you have any questions."
        );
    }

    @EventListener
    public void onFarmerRegistered(FarmerRegisteredEvent event) {
        emailChannel.send(
                event.getEmail(),
                "Farming Request Received - FarmSphere",
                "Hi " + event.getFirstName() + ",\n\n"
                        + "Your request to become a farmer has been received.\n"
                        + "Our admin team will review and notify you once approved.\n\n"
                        + "Thank you for your patience!"
        );
    }

    @EventListener
    public void onInvestorRegistered(InvestorRegisteredEvent event) {
        emailChannel.send(
                event.getEmail(),
                "Investor Request Received - FarmSphere",
                "Hi " + event.getFirstName() + ",\n\n"
                        + "Your request to become an investor has been received.\n"
                        + "Our admin team will review and notify you once approved.\n\n"
                        + "Thank you for your patience!"
        );
    }
}
