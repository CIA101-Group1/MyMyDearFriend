package com.tibame.group1.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketRegistrationResDTO {

    private String marketName;

    private String marketLocation;

    private String marketStart;

    private String marketEnd;

    private Integer marketFee;

    private Integer applicantPopulation;

    private String participateDate;

    private Integer status;
}
