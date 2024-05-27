package com.tibame.group1.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletWithdrawResDTO {

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        CREATE_SUCCESS("1", "新增異動成功");

        private final String code;
        private final String message;
    }


}
