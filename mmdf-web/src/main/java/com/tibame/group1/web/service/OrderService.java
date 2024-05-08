package com.tibame.group1.web.service;

import com.tibame.group1.common.dto.web.OrderSellerGetAllReqDTO;
import com.tibame.group1.common.dto.web.OrderSellerGetAllResDTO;
import com.tibame.group1.web.dto.OrderCreateReqDTO;
import com.tibame.group1.web.dto.OrderCreateResDTO;

public interface OrderService {

    OrderCreateResDTO orderCreate(OrderCreateReqDTO req);

    OrderSellerGetAllResDTO orderSellerGetAll(OrderSellerGetAllReqDTO req);
}
