package com.tibame.group1.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailResDTO {
    private Integer orderId;

    private Integer productId;

    private Integer quantity;

    private Integer price;

    private String name;

    private String seller;

    private String imageBase64;
}
