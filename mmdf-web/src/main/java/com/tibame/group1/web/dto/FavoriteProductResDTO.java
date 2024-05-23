package com.tibame.group1.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteProductResDTO {

    private Integer sellerId;

    private String sellerName;

    private Integer productId;

    private String productName;

    private Integer price;

    private String imageBase64;
}
