package com.tibame.group1.common.dto.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateReqDTO {

    @NotEmpty(message = "帳號請勿空白")
    @Pattern(
            regexp = "^[(一-龥)a-zA-Z0-9_]{2,20}$",
            message = "帳號：只能是中、英文字母、數字和_ , 且長度必需在2到20之間")
    private String memberAccount;

    @NotEmpty(message = "密碼：請勿空白")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$", message = "密碼：只能是數字和英文字母，且長度必須在6到16之間")
    private String cid;

    @NotEmpty(message = "姓名請勿空白")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z]{2,20}$", message = "姓名：只能是中、英文字母，且長度必需在2到20之間")
    private String name;

    @NotEmpty(message = "電話請勿空白")
    @Pattern(regexp = "^[0-9]{10}$", message = "電話：必須是10位數字，且不含符號")
    private String phone;

    @NotEmpty(message = "email請勿空白")
    @Email(message = "email格式不正確")
    private String email;

    @NotEmpty(message = "生日請勿空白")
    @Pattern(
            regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$",
            message = "生日格式必須為西元年月日(yyyymmdd)")
    private String birth;

    @NotEmpty(message = "身份證字號請勿空白")
    @Pattern(regexp = "^[A-Z][12]\\d{8}$", message = "身份證字號格式不正確")
    private String twPersonId;

    @NotEmpty(message = "城市請勿空白")
    private String city;

    @NotEmpty(message = "地址請勿空白")
    private String address;

    @NotEmpty(message = "錢包密碼請勿空白")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$", message = "錢包密碼：只能是數字和英文字母，且長度必須在6到16之間")
    private String walletCid;

    @NotEmpty(message = "錢包密碼提示問題請勿空白")
    private String walletQuestion;

    @NotEmpty(message = "錢包密碼提示答案請勿空白")
    private String walletAnswer;

    private String imageBase64;
}
