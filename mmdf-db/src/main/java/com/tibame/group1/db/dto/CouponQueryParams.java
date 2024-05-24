package com.tibame.group1.db.dto;

import com.tibame.group1.common.enums.CouponEffectCategory;
import com.tibame.group1.common.enums.CouponStackCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponQueryParams {

    private CouponStackCategory couponStackCategory;
    private CouponEffectCategory couponEffectCategory;
    private String search;
    private String orderBy;
    private String sort;
    private Integer limit;
    private Integer offset;
}
