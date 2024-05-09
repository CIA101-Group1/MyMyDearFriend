package com.tibame.group1.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderCreateReqDTO {
    @NotNull
    private Integer sellerId;

    private Integer memberCouponId1;

    private Integer memberCouponId2;

    @NotNull
    private Integer priceBeforeDiscount;

    private Integer discount;

    @NotNull
    private Integer priceAfterDiscount;

    @NotBlank(message = "姓名請勿空白")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z]{2,20}$", message = "姓名：只能是中、英文字母，且長度必需在2到20之間")
    private String name;

    @NotBlank(message = "電話請勿空白")
    @Pattern(regexp = "^[0-9]{10}$", message = "電話：必須是10位數字，且不含符號")
    private String phone;

    @NotBlank(message = "地址請勿空白")
    private String address;

    @NotEmpty
    private List<OrderBuyProductDTO> buyProductList;
}
