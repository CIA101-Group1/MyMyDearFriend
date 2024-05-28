package com.tibame.group1.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletResDTO {
  private Integer walletRequestId;

  private String memberId;

  private String status;

  private String account;

  private String requestDate;

  private String doneDate;

  private String withdrawAmount;
}
