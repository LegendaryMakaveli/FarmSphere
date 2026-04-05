package com.farmSphere.estate.service;

import com.farmSphere.estate.data.model.Cluster;
import com.farmSphere.estate.data.model.FarmEstate;
import com.farmSphere.estate.dto.request.CreateClusterRequest;
import com.farmSphere.estate.dto.request.CreateEstateRequest;
import com.farmSphere.estate.dto.response.ClusterResponse;
import com.farmSphere.estate.dto.response.EstateResponse;

import java.util.List;

public interface EstateService {
    EstateResponse createEstate(CreateEstateRequest request);
    ClusterResponse createCluster(CreateClusterRequest request);
    List<FarmEstate> getAllEstates();
    List<Cluster> getClustersByEstate(Long estateId);

}
