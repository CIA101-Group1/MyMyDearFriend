package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.EmployeeRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRoleEntity, Integer> {
    // 根據員工id找尋角色id
    @Query("SELECT e.employeeId FROM EmployeeEntity e WHERE e.employeeId = :employeeId")
    List<Integer> findRoleIdsByEmployeeId(Integer employeeId);
}
