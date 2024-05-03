package com.tibame.group1.web.dto;

import com.tibame.group1.web.annotation.CheckLogin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateReqDTO {

    private Integer productId;

    private Integer sellerId;

    private Integer categoryId;

    @NotEmpty(message = "商品名稱請勿空白")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z]{1,20}$",
            message = "商品名稱：只能是中、英文字母，且長度必需在1到20之間")
    private String name;

    @Pattern(regexp =  "^\\p{L}{0,200}$",
            message = "商品描述：格式錯誤，且長度必需在0到200之間")
//    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z]{0,200}$",
//            message = "商品描述：格式錯誤，只能是中、英文字母，且長度必需在0到200之間")
    private String description;

    @NotEmpty(message = "價格：請勿空白")
    @Pattern(regexp = "^[0-9]{1,10}$",
            message = "價格：請填入0-9數字，最高10位數")
    private Integer price;

    @NotEmpty(message = "數量：請勿空白")
    @Pattern(regexp = "^[0-9]{1,10}$",
            message = "數量：請填入0-9數字，最多10個位數")
    private Integer quantity;

    private Integer reviewStatus;

    private Integer productStatus;

    @CheckLogin
    public Integer getMemberId() {
        return null;
    }
}
