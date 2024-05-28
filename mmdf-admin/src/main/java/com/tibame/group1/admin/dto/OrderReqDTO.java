package com.tibame.group1.admin.dto;

import com.tibame.group1.common.enums.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReqDTO {
    
    private String orderId;
    
    private String buyerName;
    
    private String sellerName;
    
    private OrderStatus orderStatus;
}
