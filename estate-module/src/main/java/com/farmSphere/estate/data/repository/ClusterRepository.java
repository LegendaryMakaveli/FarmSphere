package com.farmSphere.estate.data.repository;

import com.farmSphere.estate.data.model.Cluster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClusterRepository extends JpaRepository<Cluster, Long> {
    List<Cluster> findByEstateId(Long estateId);
}
