package com.tibame.group1.common.dto.web;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderSellerGetAllReqDTO {

    @NotNull
    private Integer sellerId;
}
