package com.farmSphere.notification.listeners;

import com.farmSphere.core.event.marketplace.ProduceListedEvent;
import com.farmSphere.notification.channel.EmailChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MarketplaceListener {
    private final EmailChannel emailChannel;

    @EventListener
    public void onProduceListed(ProduceListedEvent event) {
        emailChannel.send(
                "briankachelhoffer698@gmail.com",
                "New Produce Listed - FarmSphere",
                "A farmer has listed new produce.\n\n"
                        + "Farmer   : " + event.getFarmerName() + "\n"
                        + "Crop     : " + event.getCropName() + "\n"
                        + "Quantity : " + event.getQuantity() + " " + event.getUnit() + "\n"
                        + "Price    : ₦" + event.getPricePerUnit() + " per " + event.getUnit() + "\n\n"
                        + "Login to the admin dashboard to review."
        );
    }
}
