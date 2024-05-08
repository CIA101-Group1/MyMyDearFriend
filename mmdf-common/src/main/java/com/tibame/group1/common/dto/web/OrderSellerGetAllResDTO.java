package com.tibame.group1.common.dto.web;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderSellerGetAllResDTO {
   private List<OrderResDTO> orderList;
}
