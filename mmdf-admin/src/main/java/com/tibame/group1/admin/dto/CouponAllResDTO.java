package com.tibame.group1.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CouponAllResDTO {

    private String couponID;

    private String title;

    private String lowPrice;

    private String discount;

    private String number;

    private String dateStart;

    private String dateEnd;

    private String addable;

    private String livemode;
}
