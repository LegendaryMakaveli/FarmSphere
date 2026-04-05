package com.farmSphere.notification.listeners;

import com.farmSphere.core.event.estate.PlotAssignedEvent;
import com.farmSphere.core.event.estate.PlotUnassignedEvent;
import com.farmSphere.notification.channel.EmailChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstateListener {
    private final EmailChannel emailChannel;

    @EventListener
    public void onPlotAssigned(PlotAssignedEvent event) {
        emailChannel.send(
                event.getFarmerEmail(),
                "Plot Assigned - FarmSphere",
                "A plot has been assigned to you.\n\n"
                        + "Plot ID   : " + event.getPlotId() + "\n"
                        + "Plot Size : " + event.getPlotSize() + " hectares\n"
                        + "Soil Type : " + event.getSoilType() + "\n\n"
                        + "Login to your dashboard to view your plot details."
        );
    }

    @EventListener
    public void onPlotUnassigned(PlotUnassignedEvent event) {
        emailChannel.send(
                event.getFarmerEmail(),
                "Plot Unassigned - FarmSphere",
                "Your plot (ID: " + event.getPlotId() + ") has been unassigned.\n"
                        + "Contact admin if you have any questions."
        );
    }
}
