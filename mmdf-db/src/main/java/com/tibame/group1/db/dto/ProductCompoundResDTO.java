package com.tibame.group1.db.dto;

import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.ProductCategoryEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCompoundResDTO {
    private String productId;
    private String sellerId;
    private String categoryId;
    private String name;
    private String description;
    private String price;
    private String reviewStatus;
    private String productStatus;
//    private Set<ProductImgEntity> productImgs;
    private ProductCategoryEntity productCategory;
    private MemberEntity memberEntity;
}

