package com.tibame.group1.db.dto;

import com.tibame.group1.common.enums.WalletCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletQueryParams {

    private String walletCategory;
    private String search;
    private Integer memberId;
}
