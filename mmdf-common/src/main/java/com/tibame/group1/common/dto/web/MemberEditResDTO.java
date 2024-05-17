package com.tibame.group1.common.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberEditResDTO {

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        EDIT_SUCCESS("1", "修改成功"),

        EXIST_ACCOUNT("-1", "該帳號已存在"),

        MEMBER_NOTFOUND("-2", "查無此會員資料");

        private final String code;

        private final String message;
    }
}
