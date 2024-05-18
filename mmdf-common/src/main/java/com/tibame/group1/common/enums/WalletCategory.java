package com.tibame.group1.common.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * todo 是否要加上金錢顯示
 */

/*
*todo：要如何顯示的時候還能加上$$
* */

@AllArgsConstructor
public enum WalletCategory {

    PAYMENT("0", "付款完成"),

    RECHARGE("1", "儲值成功"),

    WITHDRAWAL("2", "提領成功"),

    REFUND("3","退款完成"),

    DEPOSIT("4","入帳申請"),

    FEE("5","手續費");



    private final String code;

    private final String message;
}
