package com.tibame.group1.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRegistrationAllResDTO {

    private String name;

    private String phone;

    private String email;

    private String city;

    private String participateDate;

    private Integer status;
}
