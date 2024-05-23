package com.tibame.group1.db.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateReqDTO {

    private Integer productId;

//    private String sellerId;

    @NotEmpty(message = "商品分類請勿空白")
    private String categoryId;

    @NotEmpty(message = "商品名稱請勿空白")
    @Pattern(regexp = "^.{1,20}$",
            message = "商品名稱：格式錯誤，且長度必需在1到20之間")
    private String name;

    @Pattern(regexp =  "^.{0,200}$",
            message = "商品描述：格式錯誤，且長度必需在0到200之間")
    private String description;

    @NotEmpty(message = "價格：請勿空白")
    @Pattern(regexp = "^[0-9]{1,10}$",
            message = "價格：請填入0-9數字，最高10位數")
    private String price;

    @NotEmpty(message = "數量：請勿空白")
    @Pattern(regexp = "^[0-9]{1,10}$",
            message = "數量：請填入0-9數字，最多10個位數")
    private String quantity;

//    @NotEmpty(message = "上下架：請勿空白")
    private String reviewStatus;

//    @NotEmpty(message = "審核狀態：請勿空白")
    private String productStatus;

//    0517
    private String image;
    private Boolean updateImg;

}
