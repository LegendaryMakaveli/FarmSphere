package com.farmSphere.notification.listeners;

import com.farmSphere.core.event.marketplace.*;
import com.farmSphere.notification.channel.EmailChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MarketplaceListener {
    private final EmailChannel emailChannel;

    @Value("${app.admin.email}")
    private String adminEmail;

    @EventListener
    public void onProduceListed(ProduceListedEvent event) {
        emailChannel.send(
                adminEmail,
                "New Produce Listed - FarmSphere",
                "A farmer has listed new produce.\n\n"
                        + "Farmer   : " + event.getFarmerName() + "\n"
                        + "Crop     : " + event.getCropName() + "\n"
                        + "Quantity : " + event.getQuantity() + " " + event.getUnit() + "\n"
                        + "Price    : ₦" + event.getPricePerUnit() + " per " + event.getUnit() + "\n\n"
                        + "Login to the admin dashboard to review."
        );
    }

    @EventListener
    public void onOrderPlaced(OrderPlacedEvent event) {
        emailChannel.send(
                adminEmail,
                "New Order Placed - FarmSphere",
                "A new order has been placed.\n\n"
                        + "Order ID   : " + event.getOrderId() + "\n"
                        + "Buyer      : " + event.getBuyerName() + "\n"
                        + "Items      : " + event.getItemCount() + "\n"
                        + "Total      : ₦" + event.getTotalAmount() + "\n\n"
                        + "Login to match this order to farmers."
        );
    }

    @EventListener
    public void onOrderCancelled(OrderCancelledEvent event) {
        emailChannel.send(
                event.getBuyerEmail(),
                "Order Cancelled - FarmSphere",
                "Your order #" + event.getOrderId() + " has been cancelled.\n"
                        + "Any deducted quantities have been restored.\n\n"
                        + "You can place a new order anytime."
        );
    }

    @EventListener
    public void onOrderMatchedBuyer(OrderMatchedBuyerEvent event) {
        emailChannel.send(
                event.getBuyerEmail(),
                "Order Being Processed - FarmSphere",
                "Hi " + event.getBuyerName() + ",\n\n"
                        + "Great news! Your order #" + event.getOrderId()
                        + " has been matched and is being processed.\n"
                        + "You will be notified once your order is confirmed."
        );
    }

    @EventListener
    public void onOrderMatched(OrderMatchedEvent event) {
        emailChannel.send(
                event.getFarmerEmail(),
                "Produce Order Matched - FarmSphere",
                "An order has been matched to your produce.\n\n"
                        + "Crop       : " + event.getCropName() + "\n"
                        + "Quantity   : " + event.getQuantity() + " " + event.getUnit() + "\n"
                        + "Order ID   : " + event.getOrderId() + "\n\n"
                        + "Prepare your produce for delivery."
        );
    }

    @EventListener
    public void onSaleConfirmed(SaleConfirmedEvent event) {
        emailChannel.send(
                event.getBuyerEmail(),
                "Order Confirmed - FarmSphere",
                "Hi " + event.getBuyerName() + ",\n\n"
                        + "Your order #" + event.getOrderId() + " has been confirmed!\n"
                        + "Total paid : ₦" + event.getTotalAmount() + "\n\n"
                        + "Thank you for shopping on FarmSphere!"
        );
    }

    @EventListener
    public void onProduceSold(ProduceSoldEvent event) {
        emailChannel.send(
                event.getFarmerEmail(),
                "Produce Sold - FarmSphere",
                "Your produce has been sold!\n\n"
                        + "Crop       : " + event.getCropName() + "\n"
                        + "Quantity   : " + event.getQuantity() + "\n"
                        + "Earnings   : ₦" + event.getEarnings() + "\n\n"
                        + "Payment will be processed shortly."
        );
    }
}
