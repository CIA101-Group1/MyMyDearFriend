package com.tibame.group1.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRoleDetailResDTO {
    private String employeeName;
    private String roleName;
    private String roleDescription;
}
