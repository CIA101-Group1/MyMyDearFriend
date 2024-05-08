package com.tibame.group1.common.dto.web;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class OrderResDTO {
    private Integer orderId;

    private Integer buyerId;

    private Integer sellerId;

    private Integer memberCouponId1;

    private Integer memberCouponId2;

    private Integer priceBeforeDiscount;

    private Integer discount;

    private Integer priceAfterDiscount;

    private Date createTime;

    private Byte orderStatus;

    private String name;

    private String phone;

    private String address;

    private Integer fee;
}
