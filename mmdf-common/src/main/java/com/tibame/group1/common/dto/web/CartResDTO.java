package com.tibame.group1.common.dto.web;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResDTO {

    private String memberId;

    private Integer sellerId;

    private String sellerName;

    private String productId;

    private String productName;

    private Integer price;

    private Integer quantity;

    private Integer subtotal;

    private String imageBase64;
}
