package com.tibame.group1.common.dto.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberEditReqDTO {

    @NotEmpty
    @Pattern(
            regexp = "^[(一-龥)a-zA-Z0-9_]{2,20}$",
            message = "帳號：只能是中、英文字母、數字和_ , 且長度必需在2到20之間")
    private String memberAccount;

    @NotEmpty
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z]{2,20}$", message = "姓名：只能是中、英文字母，且長度必需在2到20之間")
    private String name;

    @NotEmpty
    @Pattern(regexp = "^[0-9]{10}$", message = "電話：必須是10位數字，且不含符號")
    private String phone;

    @NotEmpty
    @Email(message = "email格式不正確")
    private String email;

    @NotEmpty private String city;

    @NotEmpty private String address;

    private String imageBase64;
}
