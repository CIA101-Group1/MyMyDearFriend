package com.tibame.group1.admin.dto;

import com.tibame.group1.common.dto.PagesResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WalletAllResDTO {

    private PagesResDTO pages;

    private List<WalletResDTO> walletRequestList;

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        REQUEST_SUCCESS("1", "審核通過"),

        EMPLOYEE_NOTFOUND("-1", "查無此筆");

        private final String code;

        private final String message;
    }
}
