package com.farmSphere.estate.util;

import com.farmSphere.core.enums.PLOT_STATUS;
import com.farmSphere.estate.data.model.Cluster;
import com.farmSphere.estate.data.model.FarmEstate;
import com.farmSphere.estate.data.model.Plot;
import com.farmSphere.estate.dto.request.CreateClusterRequest;
import com.farmSphere.estate.dto.request.CreateEstateRequest;
import com.farmSphere.estate.dto.request.CreatePlotRequest;

import java.time.LocalDateTime;

public class Mapper {

    public static FarmEstate maptToCreateFarmEstate(CreateEstateRequest request){
        FarmEstate estate = new FarmEstate();
        estate.setName(request.getName().trim().toUpperCase());
        estate.setTotalSize(request.getTotalSize());
        estate.setLocation(request.getLocation().toLowerCase());
        estate.setDescription(request.getDescription().toUpperCase());
        estate.setCreatedAt(LocalDateTime.now());

        return estate;
    }

    public static Cluster mapToCreateCluster(CreateClusterRequest request){
        Cluster cluster = new Cluster();
        cluster.setEstateId(request.getEstateId());
        cluster.setPrimaryCropId(request.getPrimaryCropId());
        cluster.setPrimaryCropName(request.getPrimaryCropName());
        cluster.setFarmingModel(request.getFarmingModel());
        cluster.setDescription(request.getDescription());
        cluster.setCreatedAt(LocalDateTime.now());

        return cluster;
    }

    public static Plot mapToCreatePlot(CreatePlotRequest request) {
        Plot plot = new Plot();
        plot.setClusterId(request.getClusterId());
        plot.setPlotSize(request.getPlotSize());
        plot.setSoilType(request.getSoilType());
        plot.setStatus(PLOT_STATUS.AVAILABLE);

        return plot;
    }
}
