package com.tibame.group1.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MemberCouponReqDTO {

  @NotEmpty private List<MemberCouponItem> memberCouponList;



}