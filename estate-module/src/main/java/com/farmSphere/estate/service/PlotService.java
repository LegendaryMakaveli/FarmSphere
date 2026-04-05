package com.farmSphere.estate.service;

import com.farmSphere.estate.dto.request.AssignPlotRequest;
import com.farmSphere.estate.dto.request.CreatePlotRequest;
import com.farmSphere.estate.dto.response.PlotResponse;

import java.util.List;

public interface PlotService {
    PlotResponse createPlot(CreatePlotRequest request);
    PlotResponse assignPlot(Long plotId, AssignPlotRequest request);
    PlotResponse unassignPlot(Long plotId);
    List<PlotResponse> getAvailablePlots();
    List<PlotResponse> getAllPlots();
    PlotResponse getMyPlot(Long farmerId);

}
