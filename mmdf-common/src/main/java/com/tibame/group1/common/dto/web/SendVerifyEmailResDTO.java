package com.tibame.group1.common.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendVerifyEmailResDTO {

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        ALREADY_VERIFIED("0", "此信箱已完成驗證"),

        SEND_SUCCESS("1", "寄送成功"),

        MEMBER_NOTFOUND("-1", "查無此會員資料"),

        COOLDOWN_TIME_ERROR("-2", "冷卻時間為30秒，請稍後再試");

        private final String code;

        private final String message;
    }
}
