package com.farmSphere.farming.data.repository;

import com.farmSphere.farming.data.model.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
}
