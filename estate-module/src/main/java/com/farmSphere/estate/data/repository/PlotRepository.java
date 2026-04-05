package com.farmSphere.estate.data.repository;

import com.farmSphere.core.enums.PLOT_STATUS;
import com.farmSphere.estate.data.model.Plot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlotRepository extends JpaRepository<Plot, Long> {
    List<Plot> findAllByStatus(PLOT_STATUS status);
    List<Plot> findAllByClusterId(Long clusterId);
    Optional<Plot> findByAssignedFarmerId(Long farmerId);
    boolean existsByAssignedFarmerId(Long farmerId);
}
