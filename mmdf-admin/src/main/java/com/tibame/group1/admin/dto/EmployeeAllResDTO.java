package com.tibame.group1.admin.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmployeeAllResDTO {
    private String employeeId;

    private String employeeAccount;

    private String employeePassword;

    private String employeeName;

    private String employeeEmail;

    private String employeePhone;

    private String employeeGender;

    private String employeeCreateTime;

    private Integer employeeStatus;
}
