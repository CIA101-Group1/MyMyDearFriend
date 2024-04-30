package com.tibame.group1.common.dto.frontend;

import jakarta.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDTO {

    @NotEmpty(message = "帳號請勿空白")
    private String memberAccount;

    @NotEmpty(message = "密碼請勿空白")
    private String cid;
}
