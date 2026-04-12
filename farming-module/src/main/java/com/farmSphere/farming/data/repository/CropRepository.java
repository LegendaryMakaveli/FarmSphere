package com.farmSphere.farming.data.repository;

import com.farmSphere.core.enums.PRODUCE_CATEGORY;
import com.farmSphere.farming.data.model.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    Optional<Crop> findByCropNameIgnoreCase(String cropName);
    boolean existsByCropNameIgnoreCase(String cropName);
    List<Crop> findAllByCategory(PRODUCE_CATEGORY category);
}
