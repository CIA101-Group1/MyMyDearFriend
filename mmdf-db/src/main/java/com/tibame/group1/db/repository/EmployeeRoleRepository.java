package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.EmployeeEntity;
import com.tibame.group1.db.entity.EmployeeRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRoleEntity, EmployeeRoleEntity.EmployeeRoleId> {
    // 根據員工id找尋角色id
    @Query("SELECT er FROM EmployeeRoleEntity er WHERE er.id.employeeId = :employeeId")
    EmployeeRoleEntity findRoleIdByEmployeeId(@Param("employeeId") Integer employeeId);

    @Query("SELECT er.id.roleId FROM EmployeeRoleEntity er WHERE er.id.employeeId = :employeeId")
    Integer findRoleIdsByEmployeeId(@Param("employeeId") Integer employeeId);

}
