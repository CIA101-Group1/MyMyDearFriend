package com.tibame.group1.common.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResDTO {

    private String status;

    private String authorization;

    @AllArgsConstructor
    @Getter
    public enum Status {
        EMAIL_NOT_VERIFIED("0", "信箱尚未進行驗證"),

        LOGIN_SUCCESS("1", "正常登入"),

        LOGIN_INFO_INCORRECT("-1", "帳號或密碼錯誤"),

        ACCOUNT_DISABLED("-2", "帳號已停用");

        private final String code;

        private final String message;
    }
}
