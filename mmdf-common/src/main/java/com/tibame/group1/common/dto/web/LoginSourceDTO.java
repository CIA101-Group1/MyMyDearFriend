package com.tibame.group1.common.dto.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginSourceDTO {

    public static final String ATTRIBUTE = "loginSource";

    private Integer memberId;

    private String name;

    private String email;

    private Boolean isVerified;
}
