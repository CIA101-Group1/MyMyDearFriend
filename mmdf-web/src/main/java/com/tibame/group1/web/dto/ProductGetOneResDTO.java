package com.tibame.group1.web.dto;

import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.ProductCategoryEntity;
import com.tibame.group1.db.entity.ProductImgEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


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

