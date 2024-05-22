package com.tibame.group1.common.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberNoticeUpdateResDTO {
    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        UPDATE_SUCCESS("1", "更新成功"),

        MEMBER_NOTFOUND("-1", "查無會員資料");

        private final String code;

        private final String message;
    }
}
