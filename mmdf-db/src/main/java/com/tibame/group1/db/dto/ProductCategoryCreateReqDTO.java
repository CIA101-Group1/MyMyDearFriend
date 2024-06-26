package com.tibame.group1.db.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryCreateReqDTO {

    @NotEmpty(message = "請填入商品分類名稱")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z][0-9]{1,20}$",
            message = "商品分類：只能是中、英文字母、0-9數字，且長度必需在1到20之間")
    private String categoryName;

}
