package com.tibame.group1.db.dto;

import com.tibame.group1.common.enums.WalletCategory;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WalletReqDTO {


  @NotNull
  private Integer memberID;

  @NotNull
  private Integer changeAmount;

  @NotNull
  private WalletCategory changeType;
}
