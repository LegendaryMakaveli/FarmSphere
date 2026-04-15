package com.farmSphere.notification.listeners;


import com.farmSphere.core.event.investment.*;
import com.farmSphere.notification.channel.EmailChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvestmentListener {

    private final EmailChannel emailChannel;

    @Value("${app.admin.email}")
    private String adminEmail;

    @EventListener
    public void onInvestmentMade(InvestmentMadeEvent event) {
        emailChannel.send(
                event.getInvestorEmail(),
                "Investment Confirmed - FarmSphere",
                "Hi " + event.getInvestorName() + ",\n\n"
                        + "Your investment has been confirmed.\n\n"
                        + "Crop         : " + event.getCropName() + "\n"
                        + "Units Bought : " + event.getUnitsPurchased() + "\n"
                        + "Total Invested: ₦" + event.getTotalInvested() + "\n\n"
                        + "You can view your portfolio anytime in the app."
        );
    }

    @EventListener
    public void onAssetFullyFunded(AssetFullyFundedEvent event) {
        emailChannel.send(
                adminEmail,
                "Asset Fully Funded - FarmSphere",
                "Farm asset for crop plan "
                        + event.getCropName() + " is fully funded.\n"
                        + "Asset ID: " + event.getAssetId()
        );
    }

    @EventListener
    public void onTokenSold(TokenSoldEvent event) {
        emailChannel.send(
                event.getSellerEmail(),
                "Token Sale Confirmed - FarmSphere",
                "Your " + event.getUnits() + " units of "
                        + event.getCropName() + " have been sold.\n"
                        + "Total received: ₦" + event.getTotalAmount()
        );

        emailChannel.send(
                event.getBuyerEmail(),
                "Token Purchase Confirmed - FarmSphere",
                "You successfully purchased " + event.getUnits()
                        + " units of " + event.getCropName() + ".\n"
                        + "Total paid: ₦" + event.getTotalAmount()
        );
    }

    @EventListener
    public void onInvestorPaid(InvestorPaidEvent event) {
        emailChannel.send(
                event.getInvestorEmail(),
                "ROI Payment - FarmSphere",
                "Hi " + event.getInvestorName() + ",\n\n"
                        + "Your ROI has been distributed!\n\n"
                        + "Crop           : " + event.getCropName() + "\n"
                        + "Units Owned    : " + event.getUnits() + "\n"
                        + "Total Invested : ₦" + event.getTotalInvested() + "\n"
                        + "ROI Earned     : ₦" + event.getRoiEarned()
                        + " (" + event.getActualROI() + "%)\n"
                        + "Total Payout   : ₦" + event.getTotalPayout() + "\n\n"
                        + "Payment has been processed to your wallet."
        );
    }

    @EventListener
    public void onYieldDistributed(YieldDistributedEvent event) {
        emailChannel.send(
                adminEmail,
                "Yield Distribution Complete - FarmSphere",
                "ROI distribution complete for " + event.getCropName() + ".\n\n"
                        + "Total Paid Out  : ₦" + event.getTotalPaidOut() + "\n"
                        + "Investors Paid  : " + event.getTotalInvestorsPaid() + "\n"
                        + "Actual ROI      : " + event.getActualROI() + "%"
        );
    }
}

