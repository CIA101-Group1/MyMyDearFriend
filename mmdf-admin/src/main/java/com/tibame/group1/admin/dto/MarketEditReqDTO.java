package com.tibame.group1.admin.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(
            regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$",
            message = "日期格式必須為西元年月日(yyyymmdd)")
    private String marketStart;

    @NotEmpty(message = "活動結束日期請勿空白")
    @Pattern(
            regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$",
            message = "日期格式必須為西元年月日(yyyymmdd)")
    private String marketEnd;

    private Integer marketFee;

    private Integer applicantLimit;

    @NotEmpty(message = "報名開始日期請勿空白")
    @Pattern(
            regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$",
            message = "日期格式必須為西元年月日(yyyymmdd)")
    private String startDate;

    @NotEmpty(message = "報名結束日期請勿空白")
    @Pattern(
            regexp = "^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$",
            message = "日期格式必須為西元年月日(yyyymmdd)")
    private String endDate;

    private Integer marketStatus;

}




