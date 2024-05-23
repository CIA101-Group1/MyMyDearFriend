package com.tibame.group1.common.dto.web;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BidProductAddReqDTO {

    @NotNull(message = "商品分類編號：請勿空白")
    private Integer categoryId;

    @NotNull(message = "商品狀況編號：請勿空白")
    private Integer conditionId;

    @NotEmpty(message = "商品名稱：請勿空白")
    @Size(max = 40)
    private String name;

    @NotEmpty(message = "商品介紹：請勿空白")
    @Size(max = 400)
    private String description;

    @NotNull(message = "起標價格：請勿空白")
    @Positive(message = "起標價格：必須為正整數")
    @Max(value = 9999999, message = "起標價格：金額不能超過{value}")
    private Integer startPrice;

    @NotNull(message = "競標時間長度：請勿空白")
    @Max(value = 9999999, message = "競標時間長度：")
    private Integer duration;

    @Size(min = 1, max = 3, message = "商品圖片：請上傳{min}到{max}張圖片")
    private MultipartFile[] images;
}
