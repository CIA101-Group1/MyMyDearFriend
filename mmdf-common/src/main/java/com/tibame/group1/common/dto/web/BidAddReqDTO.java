package com.tibame.group1.common.dto.web;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BidAddReqDTO {
    @NotNull(message = "商品編號：請勿空白")
    private Integer productId;

    @NotNull(message = "出價金額：請勿空白")
    @Positive(message = "出價金額：必須為正整數")
    @Max(value = Integer.MAX_VALUE, message = "出價金額：金額不能超過{value}")
    private Integer amount;

}
