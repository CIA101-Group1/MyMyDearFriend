package com.tibame.group1.common.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCidResetResDTO {

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        VERIFY_CODE_EXPIRED("0", "驗證碼過期"),

        RESET_CID_SUCCESS("1", "重設密碼成功"),

        CHECK_CID_DIFFERENCE("-1", "二次設定新密碼不同"),

        VERIFY_CODE_ERROR("-2", "驗證碼錯誤");

        private final String code;

        private final String message;
    }
}
