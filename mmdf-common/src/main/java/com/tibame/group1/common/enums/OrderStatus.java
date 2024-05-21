package com.tibame.group1.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    TO_SHIP((byte) 0, "待出貨"),

    SHIPPED((byte) 1, "已出貨"),

    COMPLETED((byte) 2, "完成"),

    RETURN((byte) 3, "退貨");

    private final Byte code;

    private final String message;
}
