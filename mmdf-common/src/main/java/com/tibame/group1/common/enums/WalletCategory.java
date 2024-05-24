package com.tibame.group1.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * todo 是否要加上金錢顯示
 */


@AllArgsConstructor
@Getter
public enum WalletCategory {

    PAYMENT("0", "付款完成"),

    TOP_UP("1", "儲值成功"),

    WITHDRAW("2", "提款成功"),

    REFUND("3", "退款完成"),

    DEPOSIT("4", "入帳成功"),

    FEE("5", "扣手續費"),

    TEST("6", "測試使用"),

    MARKET("7", "市集報名費");

    private final String code;
    private final String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static WalletCategory fromCode(String code) {
        for (WalletCategory category : WalletCategory.values()) {
            if (category.getCode().equals(code)) {
                return category;
            }
        }
        throw new IllegalArgumentException("無效的代碼: " + code);
    }
}
