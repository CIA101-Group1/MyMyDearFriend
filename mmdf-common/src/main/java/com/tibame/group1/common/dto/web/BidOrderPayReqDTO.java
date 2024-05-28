package com.tibame.group1.common.dto.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidOrderPayReqDTO {

    @NotEmpty(message = "姓名：請勿空白")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z]{2,20}$", message = "姓名：只能是中、英文字母，且長度必需在2到20之間")
    private String name;

    @NotEmpty(message = "手機號碼：請勿空白")
    @Pattern(regexp = "^[0-9]{10}$", message = "手機號碼：必須是10位數字，且不含符號")
    private String phone;

    @NotEmpty(message = "email：請勿空白")
    @Email(message = "email格式不正確")
    private String email;

    @NotEmpty(message = "地址：請勿空白")
    private String address;

    @NotEmpty(message = "錢包密碼：請勿空白")
    private String walletCid;

    @NotNull(message = "優惠金額：請勿空白")
    private Integer discount;

    @NotNull(message = "合計金額：請勿空白")
    private Integer total;
}
