package com.tibame.group1.common.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCidForgetResDTO {

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        EMAIL_SEND_SUCCESS("1", "成功發送重設密碼信"),

        EMAIL_NOTFOUND("-1", "查無此信箱，請前往註冊"),

        COOLDOWN_TIME_ERROR("-2", "冷卻時間為30秒，請稍後再試");

        private final String code;

        private final String message;
    }
}
