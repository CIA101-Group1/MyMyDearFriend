package com.tibame.group1.common.enums;

import lombok.AllArgsConstructor;

/**
 * todo 是否要加上金錢顯示
 */


@AllArgsConstructor
public enum WalletCategory {

    PAYMENT("0", "付款完成"),

    TOP_UP("1", "儲值成功"),

    WITHDRAWAL("2", "提領成功"),

    REFUND("3","退款完成"),

    DEPOSIT("4","入帳申請"),

    FEE("5","手續費"),

    TEST("6", "測試用");



    private final String code;
    private final String message;
}
