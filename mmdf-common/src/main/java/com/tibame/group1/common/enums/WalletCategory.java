package com.tibame.group1.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** todo 是否要加上金錢顯示 */
@AllArgsConstructor
@Getter
public enum WalletCategory {
    PAYMENT(0, "付款"),

    TOP_UP(1, "儲值"),

    WITHDRAW(2, "提款"),

    REFUND(3, "退款"),

    DEPOSIT(4, "入帳"),

    FEE(5, "手續費"),

    MARKET(6, "市集報名費");

    private final int code;

    private final String message;

    // 根據代碼查找對應的enum值
    public static WalletCategory fromCode(int code) {
        for (WalletCategory category : WalletCategory.values()) {
            if (category.getCode() == code) {
                return category;
            }
        }
        throw new IllegalArgumentException("無效的代碼: " + code);
    }
}
