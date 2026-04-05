package com.farmSphere.estate.service;


import com.farmSphere.estate.data.model.Cluster;
import com.farmSphere.estate.data.model.FarmEstate;
import com.farmSphere.estate.data.repository.ClusterRepository;
import com.farmSphere.estate.data.repository.FarmEstateRepository;
import com.farmSphere.estate.dto.request.CreateClusterRequest;
import com.farmSphere.estate.dto.request.CreateEstateRequest;
import com.farmSphere.estate.dto.response.ClusterResponse;
import com.farmSphere.estate.dto.response.EstateResponse;
import com.farmSphere.estate.util.Mapper;
import com.farmSphere.infrastructure.eventbus.DomainEventPublisher;
import com.farmSphere.infrastructure.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.farmSphere.estate.util.Mapper.mapToCreateCluster;

@Service
@RequiredArgsConstructor
public class EstateServiceImplementation implements EstateService{
    private final FarmEstateRepository estateRepository;
    private final ClusterRepository clusterRepository;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public EstateResponse createEstate(CreateEstateRequest request) {
      estateRepository.findByName(request.getName()).ifPresent(e -> {throw new DomainException("Estate already exists!", 409);});
      FarmEstate estate = Mapper.maptToCreateFarmEstate(request);
      estateRepository.save(estate);

      return EstateResponse.builder()
        .estateId(estate.getId())
        .name(estate.getName())
        .location(estate.getLocation())
        .description(estate.getDescription())
        .totalSize(estate.getTotalSize())
        .createdAt(estate.getCreatedAt() != null ? formatter.format(estate.getCreatedAt()) : null)
        .build();
    }

    @Override
    public ClusterResponse createCluster(CreateClusterRequest request) {
        clusterRepository.findById(request.getEstateId()).ifPresent(c -> {throw new DomainException("Cluster already exists for this estate!", 409);});
        Cluster cluster = mapToCreateCluster(request);
        clusterRepository.save(cluster);

        return ClusterResponse.builder()
                .clusterId(cluster.getClusterId())
                .clusterName(cluster.getClusterName())
                .createdAt(cluster.getCreatedAt() != null ? formatter.format(cluster.getCreatedAt()) : null)
                .build();
    }

    @Override
    public List<FarmEstate> getAllEstates() {
        return estateRepository.findAll();
    }

    @Override
    public List<Cluster> getClustersByEstate(Long estateId) {
        return clusterRepository.findByEstateId(estateId);
    }
}
