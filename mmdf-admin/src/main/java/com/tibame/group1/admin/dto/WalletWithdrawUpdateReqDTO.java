package com.tibame.group1.admin.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletWithdrawUpdateReqDTO {

    @NotEmpty
    private String walletRequestId;
}
