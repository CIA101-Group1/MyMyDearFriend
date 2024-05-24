package com.tibame.group1.web.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCouponItem
{

    @NotNull
    private Integer couponID;

    @NotNull
    private Integer stack;
}
