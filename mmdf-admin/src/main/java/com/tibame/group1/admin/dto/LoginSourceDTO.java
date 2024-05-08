package com.tibame.group1.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginSourceDTO {

    public static final String ATTRIBUTE = "loginSource";

    private Integer employeeId;

    private String employeeName;

}
