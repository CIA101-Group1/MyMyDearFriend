package com.tibame.group1.web.dto;

import com.tibame.group1.common.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateReqDTO {
    @NotNull
    private Integer orderId;

    @NotNull
    private OrderStatus orderStatus;
}
