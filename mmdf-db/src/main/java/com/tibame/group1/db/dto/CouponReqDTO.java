package com.tibame.group1.db.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tibame.group1.common.enums.CouponStackCategory;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateStart;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateEnd;

    @NotNull
    private CouponStackCategory addable;

    @NotNull
    private String livemode;


}
