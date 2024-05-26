package com.tibame.group1.web.dto;

import com.tibame.group1.common.enums.WalletCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletCreateReqDTO {

  private Integer changeAmount;

  private WalletCategory walletCategory;
}
