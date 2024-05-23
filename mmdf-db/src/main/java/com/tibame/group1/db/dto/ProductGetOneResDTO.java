package com.tibame.group1.db.dto;

import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.ProductCategoryEntity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductGetOneResDTO {
    private Integer productId;
    private Integer sellerId;
    private Integer categoryId;
    private String name;
    private String description;
    private Integer price;
    private Integer reviewStatus;
    private Integer productStatus;
//    private Set<ProductImgEntity> productImgs;
    private ProductCategoryEntity productCategory;
    private MemberEntity memberEntity;
}

