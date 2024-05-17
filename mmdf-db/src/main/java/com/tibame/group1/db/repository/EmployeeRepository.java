package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    EmployeeEntity findByEmployeeAccount(@Param("employeeAccount")String employeeAccount);


}
