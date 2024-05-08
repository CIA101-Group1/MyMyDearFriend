package com.tibame.group1.common.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVerifyResDTO {

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        VERIFY_CODE_EXPIRED("0", "驗證碼過期"),

        VERIFY_SUCCESS("1", "驗證成功"),

        VERIFY_CODE_ERROR("-1", "驗證碼錯誤"),

        VERIFY_CODE_INVALID("-2", "驗證碼失效");

        private final String code;

        private final String message;
    }
}
