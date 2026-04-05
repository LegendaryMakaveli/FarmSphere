package com.farmSphere.estate.dto.response;


import com.farmSphere.estate.data.model.Plot;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;

@Data
@Builder
public class PlotResponse {
    private Long plotId;
    private Long clusterId;
    private float plotSize;
    private String soilType;
    private String status;
    private Long assignedFarmerId;
    private String assignedDate;
    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static PlotResponse from(Plot plot) {
        return PlotResponse.builder()
                .plotId(plot.getPlotId())
                .clusterId(plot.getClusterId())
                .plotSize(plot.getPlotSize())
                .soilType(plot.getSoilType())
                .status(plot.getStatus().name())
                .assignedFarmerId(plot.getAssignedFarmerId())
                .assignedDate(plot.getAssignedDate() != null ? FORMATTER.format(plot.getAssignedDate()) : null)
                .build();
    }
}
