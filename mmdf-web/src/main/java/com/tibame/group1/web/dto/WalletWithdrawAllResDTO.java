package com.tibame.group1.web.dto;

import com.tibame.group1.common.dto.PagesResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WalletWithdrawAllResDTO {

    private PagesResDTO pages;

    private List<WalletWithdrawCreateResDTO> walletWithdrawList;

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        QUERY_SUCCESS("1", "查詢成功"),

        WALLET_WITHDRAW_NOTFOUND("-1", "查無任何資料");

        private final String code;

        private final String message;
    }
}
