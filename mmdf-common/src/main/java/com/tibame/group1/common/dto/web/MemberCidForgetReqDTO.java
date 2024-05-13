package com.tibame.group1.common.dto.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCidForgetReqDTO {

    @NotEmpty(message = "信箱請勿空白")
    private String email;
}
