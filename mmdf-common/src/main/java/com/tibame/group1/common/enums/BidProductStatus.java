package com.tibame.group1.common.enums;

public enum BidProductStatus {
    REJECT(-1, "不通過"),
    PENDING(0, "待審核"),
    APPROVE(1, "已通過"),
    START(2, "競標中"),
    END(3, "已結束");

    private final int value;
    private final String description;

    BidProductStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static BidProductStatus fromValue(int value) {
        for (BidProductStatus status : BidProductStatus.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid BidProductStatus value: " + value);
    }
}
