package com.tibame.group1.db.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImgCreateReqDTO {

    @NotEmpty(message = "商品編號請勿空白")
    private String productId;

    @NotEmpty(message = "請載入商品圖片以便審查及觀看")
    @Size(min = 1, message = "圖片大小：請提供至少 1byte")
    private String image;

}
