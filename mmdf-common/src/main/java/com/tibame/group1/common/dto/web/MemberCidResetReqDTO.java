package com.tibame.group1.common.dto.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCidResetReqDTO {

    @NotEmpty private String verifyCode;

    @NotEmpty private String newCid;

    @NotEmpty private String newCidCheck;
}
