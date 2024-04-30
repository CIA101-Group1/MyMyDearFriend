package com.tibame.group1.common.dto.frontend;

import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVerifyReqDTO {
    @NotEmpty private String verifyCode;
}
