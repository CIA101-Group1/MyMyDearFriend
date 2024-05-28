package com.tibame.group1.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketRegistrationReqDTO {
    private Integer marketId;

    private Integer memberId;

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        PAY_SUCCESS("1", "報名成功，已付款"),

        PAY_NO_SUCCESS("-1", "報名成功，尚未付款");

        private final String code;

        private final String message;
    }
}
