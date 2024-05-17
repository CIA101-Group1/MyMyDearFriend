package com.tibame.group1.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketEditResDTO {
    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        EDIT_SUCCESS("1", "修改成功"),

        MEMBER_NOTFOUND("-1", "查無此會員資料");

        private final String code;

        private final String message;
    }
}
