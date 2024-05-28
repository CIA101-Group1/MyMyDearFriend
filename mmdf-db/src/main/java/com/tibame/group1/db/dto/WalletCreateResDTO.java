package com.tibame.group1.db.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletCreateResDTO {

    private Integer changeAmount;

    private String changeTime;

    private String changeType;

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        CREATE_SUCCESS("1", "新增異動成功"),

        DO_NOT_EXIST_ACCOUNT("-1", "該帳號不存在"),

        INSUFFICIENT_BALANCE("-2", "錢包餘額不足");

        private final String code;
        private final String message;
    }
}
