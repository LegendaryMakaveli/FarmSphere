package com.farmSphere.tool.data.repository;

import com.farmSphere.tool.data.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ToolRepository extends JpaRepository <Tool, Long>{
}
