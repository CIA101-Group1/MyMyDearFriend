package com.tibame.group1.admin.dto;

import com.tibame.group1.db.entity.EmployeeEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeResDTO {
    private List<EmployeeAllResDTO> employeeList;
}
