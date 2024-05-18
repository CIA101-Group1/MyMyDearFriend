package com.tibame.group1.db.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class WalletReqDTO {


  @NotNull
  private Integer memberID;

  @NotNull
  private Integer changeAmount;

  @NotNull
  private Integer changeType;
}
