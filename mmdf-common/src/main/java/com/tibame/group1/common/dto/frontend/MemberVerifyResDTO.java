package com.tibame.group1.common.dto.frontend;

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

        USER_NOT_FOUND("-1", "找不到對應使用者"),

        VERIFY_CODE_ERROR("-2", "驗證碼錯誤");

        private final String code;

        private final String message;
    }
}
