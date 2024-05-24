package com.tibame.group1.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketDetailResDTO {
    private Integer marketId;

    private String marketName;

    private String marketDescription;

    private String marketImage;

    private String marketLocation;

    private String marketStart;

    private String marketEnd;

    private Integer marketFee;

    private Integer applicantPopulation;

    private Integer applicantLimit;

    private String startDate;

    private String endDate;

    private Integer marketStatus;

    private String status;

    @AllArgsConstructor
    @Getter
    public enum Status {
        QUERY_SUCCESS("1", "查詢成功"),

        MEMBER_NOTFOUND("-1", "查無該市集活動資料");

        private final String code;

        private final String message;
    }

}
