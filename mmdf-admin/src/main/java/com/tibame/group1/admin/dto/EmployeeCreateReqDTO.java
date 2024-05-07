package com.tibame.group1.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeCreateReqDTO {
    @NotEmpty(message = "帳號請勿空白")
    @Pattern(
            regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$",
            message = "帳號：只能是中、英文字母、數字和_ , 且長度必需在2到20之間")
    private String employeeAccount;

    @NotEmpty(message = "密碼：請勿空白")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,16}$", message = "密碼：只能是數字和英文字母，且長度必須在6到16之間")
    private String employeePassword;

    @NotEmpty(message = "姓名請勿空白")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z]{2,20}$", message = "姓名：只能是中、英文字母，且長度必需在2到20之間")
    private String employeeName;

    @NotEmpty(message = "email請勿空白")
    @Email(message = "email格式不正確")
    private String employeeEmail;

    @NotEmpty(message = "電話請勿空白")
    @Pattern(regexp = "^[0-9]{10}$", message = "電話：必須是10位數字，且不含符號")
    private String employeePhone;

    @NotEmpty(message = "性別請勿空白")
    private String employeeGender;


}
