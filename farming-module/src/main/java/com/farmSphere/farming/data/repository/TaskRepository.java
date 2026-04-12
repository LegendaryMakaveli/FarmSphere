package com.farmSphere.farming.data.repository;

import com.farmSphere.core.enums.TASK_STATUS;
import com.farmSphere.farming.data.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByFarmCycleId(Long farmCycleId);
    List<Task> findAllByAssignedFarmerId(Long farmerId);
    List<Task> findAllByAssignedFarmerIdAndStatus(Long farmerId, TASK_STATUS status);
}
