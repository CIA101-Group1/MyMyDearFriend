package com.tibame.group1.admin.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MarketEditReqDTO {
    private Integer marketId;

    @NotEmpty(message = "活動名稱請勿空白")
    private String marketName;

    @NotEmpty(message = "活動說明請勿空白")
    private String marketDescription;

    private String marketImage;

    @NotEmpty(message = "活動地點請勿空白")
    private String marketLocation;

    @NotEmpty(message = "活動開始日期請勿空白")
    private String marketStart;

    @NotEmpty(message = "活動結束日期請勿空白")
    private String marketEnd;

    @NotEmpty(message = "活動費用請勿空白")
    private Integer marketFee;

    @NotEmpty(message = "報名人數上限請勿空白")
    private Integer applicantLimit;

    @NotEmpty(message = "開始報名日期請勿空白")
    private String startDate;

    @NotEmpty(message = "結束報名日期請勿空白")
    private String endDate;

}




