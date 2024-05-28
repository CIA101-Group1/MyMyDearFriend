package com.tibame.group1.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletWithdrawCreateResDTO {

    private String status;

    private String requestDate;

    private String doneDate;

    private String withdrawAmount;

    @AllArgsConstructor
    @Getter
    public enum Status {
        REQUEST_SUBMITTED("1", "新增異動成功"),

        DO_NOT_EXIST_ACCOUNT("-1", "該帳號不存在"),

        INSUFFICIENT_BALANCE("-2", "錢包餘額不足");

        private final String code;
        private final String message;
    }
}
