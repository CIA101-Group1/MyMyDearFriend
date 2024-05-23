package com.tibame.group1.common.enums;

public enum BidOrderStatus {
    UNPAID(1, "待付款"),
    UNSHIPPED(2, "待出貨"),
    DELIVERED(3, "已送達"),
    COMPLETED(4, "已完成"),
    CANCELED(5, "已取消");

    private final int code;
    private final String message;

    BidOrderStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static BidOrderStatus fromCode(int code) {
        for (BidOrderStatus status : BidOrderStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid BidOrder code: " + code);
    }
}
