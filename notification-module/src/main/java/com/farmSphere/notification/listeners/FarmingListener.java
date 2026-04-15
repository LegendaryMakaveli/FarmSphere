package com.farmSphere.notification.listeners;

import com.farmSphere.core.event.farming.*;
import com.farmSphere.notification.channel.EmailChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FarmingListener {

    private final EmailChannel emailChannel;

    @Value("${app.admin.email}")
    private String adminEmail;

    @EventListener
    public void onFarmCycleStarted(FarmCycleStartedEvent event) {
        emailChannel.send(
                event.getFarmerEmail(),
                "Farm Cycle Started - FarmSphere",
                "Hi,\n\n"
                        + "A farm cycle has been started for your plot.\n\n"
                        + "Crop       : " + event.getPrimaryCropName() + "\n"
                        + "Plot ID    : " + event.getPlotId() + "\n"
                        + "Cycle ID   : " + event.getFarmCycleId() + "\n\n"
                        + "Login to view your tasks and crop plan."
        );
    }

    @EventListener
    public void onFarmCycleHarvested(FarmCycleHarvestedEvent event) {
        emailChannel.send(
                adminEmail,
                "Harvest Recorded - FarmSphere",
                "A farm cycle has been harvested.\n\n"
                        + "Crop       : " + event.getCropName() + "\n"
                        + "Plot ID    : " + event.getPlotId() + "\n"
                        + "Total Yield: " + event.getTotalPrimaryYieldKg() + " KG\n"
                        + "Cycle ID   : " + event.getFarmCycleId() + "\n\n"
                        + "ROI calculation has been triggered for linked farm asset."
        );
    }

    @EventListener
    public void onIntercropAdded(IntercropAddedEvent event) {
        emailChannel.send(
                adminEmail,
                "Intercrop Added - FarmSphere",
                "A farmer has added an intercrop.\n\n"
                        + "Crop       : " + event.getCropName() + "\n"
                        + "Plot ID    : " + event.getPlotId() + "\n"
                        + "Farmer ID  : " + event.getFarmerId() + "\n"
                        + "CropPlan ID: " + event.getCropPlanId()
        );
    }
}