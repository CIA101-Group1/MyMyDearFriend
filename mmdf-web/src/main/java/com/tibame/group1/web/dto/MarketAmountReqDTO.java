package com.tibame.group1.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketAmountReqDTO {

    @NotNull
    private Integer memberID;

    @NotNull
    private Integer marketAmount;

}
