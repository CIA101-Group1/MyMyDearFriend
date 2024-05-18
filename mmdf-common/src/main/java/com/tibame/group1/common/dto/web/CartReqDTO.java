package com.tibame.group1.common.dto.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartReqDTO {

    private String memberId;

    @NotBlank
    private String productId;

    @NotNull
    private Integer quantity;
}
