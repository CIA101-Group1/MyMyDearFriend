package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.EmployeeEntity;
import com.tibame.group1.db.entity.EmployeeRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRoleEntity, Integer> {
    //通過employeeEntity查詢對應的角色
    List<EmployeeRoleEntity> findByEmployee(EmployeeEntity employee);
}
