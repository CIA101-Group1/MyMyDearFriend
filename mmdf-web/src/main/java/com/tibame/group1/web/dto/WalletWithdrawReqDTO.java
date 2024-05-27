package com.tibame.group1.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletWithdrawReqDTO {

  private Integer changeAmount;

  @NotEmpty(message = "數字長度必須為 10 至 16 位")
  @Pattern(regexp = "^\\d{10,16}$", message = "數字長度必須為 10 至 16 位")
  private String account;
}
