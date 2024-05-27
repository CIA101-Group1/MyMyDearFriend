package com.tibame.group1.admin.dto;

import com.tibame.group1.common.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateReqDTO {

    @NotNull
    Integer orderId;

    @NotNull
    OrderStatus orderStatus;
}
