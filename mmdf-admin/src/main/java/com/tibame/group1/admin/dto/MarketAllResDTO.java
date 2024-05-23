package com.tibame.group1.admin.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MarketAllResDTO {
    private String marketId;

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

}
