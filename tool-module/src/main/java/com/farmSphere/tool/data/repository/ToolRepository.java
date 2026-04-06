package com.farmSphere.tool.data.repository;

import com.farmSphere.tool.data.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ToolRepository extends JpaRepository <Tool, Long>{
    List<Tool> findAllByAvailabilityStatusTrue();
    Optional<Tool> findByToolNameIgnoreCase(String toolName);
}
