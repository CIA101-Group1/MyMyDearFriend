package com.tibame.group1.db.dto;

import com.tibame.group1.common.enums.CouponCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponQueryParams {

    private CouponCategory CouponCategory;
    private String search;
    private String orderBy;
    private String sort;
    private Integer limit;
    private Integer offset;
}
