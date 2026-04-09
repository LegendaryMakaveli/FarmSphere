package com.farmSphere.estate.service;

import com.farmSphere.core.enums.PLOT_STATUS;
import com.farmSphere.core.event.estate.PlotAssignedEvent;
import com.farmSphere.core.event.estate.PlotUnassignedEvent;
import com.farmSphere.estate.data.model.Plot;
import com.farmSphere.estate.data.repository.ClusterRepository;
import com.farmSphere.estate.data.repository.PlotRepository;
import com.farmSphere.estate.dto.request.AssignPlotRequest;
import com.farmSphere.estate.dto.request.CreatePlotRequest;
import com.farmSphere.estate.dto.response.PlotResponse;
import com.farmSphere.infrastructure.eventbus.DomainEventPublisher;
import com.farmSphere.infrastructure.exception.DomainException;
import com.farmSphere.infrastructure.security.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.farmSphere.estate.util.Mapper.mapToCreatePlot;


@Service
@RequiredArgsConstructor
public class PlotServiceImplementation implements PlotService{
    private final PlotRepository plotRepository;
    private final ClusterRepository clusterRepository;
    private final DomainEventPublisher eventPublisher;


    @Override
    public PlotResponse createPlot(CreatePlotRequest request) {
        SecurityUtils.requireAdmin();
        clusterRepository.findById(request.getClusterId()). orElseThrow(() -> new DomainException("Cluster not found", 404));
        Plot plot = mapToCreatePlot(request);

        return PlotResponse.from(plotRepository.save(plot));
    }

    @Transactional
    @Override
    public PlotResponse assignPlot(Long plotId, AssignPlotRequest request) {
        SecurityUtils.requireAdmin();
        Plot plot = plotRepository.findById(plotId).orElseThrow(() -> new DomainException("Plot not found", 404));
        if(plot.getStatus() != PLOT_STATUS.AVAILABLE) throw new DomainException("Plot is already assigned", 400);
        if (plotRepository.existsByAssignedFarmerId(request.getFarmerId())) throw new DomainException("Farmer already has an assigned plot", 409);
        plot.setAssignedFarmerId(request.getFarmerId());
        plot.setFarmerEmail(request.getFarmerEmail());
        plot.setStatus(PLOT_STATUS.ASSIGNED);
        plot.setAssignedDate(LocalDateTime.now());
        Plot saved = plotRepository.save(plot);

        eventPublisher.publish(new PlotAssignedEvent(
                saved.getPlotId(),
                saved.getAssignedFarmerId(),
                saved.getFarmerEmail(),
                saved.getPlotSize(),
                saved.getSoilType()
        ));



        return PlotResponse.from(saved);
    }

    @Transactional
    @Override
    public PlotResponse unassignPlot(Long plotId) {
        SecurityUtils.requireAdmin();
        Plot plot = plotRepository.findById(plotId).orElseThrow(() -> new DomainException("Plot not found", 404));
        if (plot.getStatus() != PLOT_STATUS.ASSIGNED) throw new DomainException("Plot is not currently assigned", 400);
        Long previousFarmerId = plot.getAssignedFarmerId();

        plot.setAssignedFarmerId(null);
        plot.setAssignedDate(null);
        plot.setStatus(PLOT_STATUS.AVAILABLE);
        Plot saved = plotRepository.save(plot);

        eventPublisher.publish(new PlotUnassignedEvent(
                saved.getPlotId(),
                saved.getFarmerEmail(),
                previousFarmerId
        ));

        return PlotResponse.from(saved);
    }

    @Override
    public List<PlotResponse> getAvailablePlots() {
        SecurityUtils.requireAdmin();
        return plotRepository.findAllByStatus(PLOT_STATUS.AVAILABLE)
                .stream()
                .map(PlotResponse::from)
                .toList();
    }

    @Override
    public List<PlotResponse> getAllPlots() {
        SecurityUtils.requireAdmin();
        return plotRepository.findAll()
                .stream()
                .map(PlotResponse::from)
                .toList();
    }

    @Override
    public PlotResponse getMyPlot(Long farmerId) {
        SecurityUtils.requireFarmer();
        Plot plot = plotRepository.findByAssignedFarmerId(farmerId).orElseThrow(() -> new DomainException("No plot assigned to you yet", 404));
        return PlotResponse.from(plot);
    }
}
