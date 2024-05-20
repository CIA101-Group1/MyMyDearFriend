package com.tibame.group1.db.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CouponReqDTO {


    @NotNull
    private String title;

    @NotNull
    private Integer lowPrice;

    @NotNull
    private Integer discount;

    @NotNull
    private Integer number;

    @NotNull
    private Date dateStart;

    @NotNull
    private Date dateEnd;

    @NotNull
    private String addable;

    @NotNull
    private String livemode;


}
