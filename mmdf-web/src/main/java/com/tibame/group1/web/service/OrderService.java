package com.tibame.group1.web.service;

import com.tibame.group1.common.dto.web.LoginSourceDTO;
import com.tibame.group1.db.entity.OrderDetailEntity;
import com.tibame.group1.db.entity.OrderEntity;
import com.tibame.group1.web.dto.*;

import java.util.List;

public interface OrderService {

    OrderCreateResDTO orderCreate(OrderCreateReqDTO req, LoginSourceDTO loginSource);

    List<OrderEntity> orderSellerGetAll(LoginSourceDTO loginSource);

    List<OrderEntity> orderBuyerGetAll(LoginSourceDTO loginSource);

    List<OrderDetailEntity> orderDetail(Integer orderId);
}
