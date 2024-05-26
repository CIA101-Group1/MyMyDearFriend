package com.tibame.group1.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderResDTO {
    private Integer orderId;

    private Integer buyerId;
    
    private Integer sellerId;

    private String buyerName;

    private String sellerName;

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

    private List<OrderDetailResDTO> orderDetailList;
}
