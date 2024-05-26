package com.tibame.group1.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletCreateResDTO {

  private Integer changeAmount;

  private String changeTime;

  private String changeType;

  private String status;

  @AllArgsConstructor
  @Getter
  public enum Status {
    CREATE_SUCCESS("1", "新增成功"),

    // 是否扣款成功

    // 是否加值成功

    DO_NOT_EXIST_ACCOUNT("-1", "該帳號不存在");

    private final String code;
    private final String message;
  }
}
