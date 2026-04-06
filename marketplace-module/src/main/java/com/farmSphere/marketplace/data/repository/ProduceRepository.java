package com.farmSphere.marketplace.data.repository;

import com.farmSphere.core.enums.PRODUCE_CATEGORY;
import com.farmSphere.core.enums.PRODUCE_STATUS;
import com.farmSphere.marketplace.data.model.Produce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProduceRepository extends JpaRepository<Produce, Long> {
    List<Produce> findAllByFarmerId(Long farmerId);
    List<Produce> findAllByStatus(PRODUCE_STATUS status);
    List<Produce> findAllByStatusAndCategory(PRODUCE_STATUS status, PRODUCE_CATEGORY category);
    Optional<Produce> findByProduceIdAndFarmerId(Long produceId, Long farmerId);
}
