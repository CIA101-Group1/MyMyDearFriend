package com.tibame.group1.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletCreateReqDTO {

    private Integer changeAmount;

    private String walletCategory;
}
