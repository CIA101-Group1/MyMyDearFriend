package com.tibame.group1.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderBuyProductDTO {

    @NotNull
    private Integer productId;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer price;
}
