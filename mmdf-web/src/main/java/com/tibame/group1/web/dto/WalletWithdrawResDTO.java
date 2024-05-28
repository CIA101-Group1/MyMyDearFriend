package com.tibame.group1.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletWithdrawResDTO {

    private String status;

    private String requestDate;

    private String withdrawAmount;

    @AllArgsConstructor
    @Getter
    public enum Status {
        REQUEST_NONE("0", "未審核"),

        REQUEST_ACCESS("1", "已審核");

        private final String code;
        private final String message;
    }
}
