package com.tibame.group1.web.service;

import com.tibame.group1.common.enums.OrderMemberStatus;
import com.tibame.group1.db.entity.OrderEntity;
import com.tibame.group1.web.dto.*;

import java.util.List;

public interface OrderService {

    OrderResDTO orderBuyerGetById(Integer orderId, LoginSourceDTO loginSource, OrderMemberStatus status);

    List<OrderResDTO> orderGetAll(LoginSourceDTO loginSource, OrderMemberStatus status);

    List<OrderDetailResDTO> orderDetail(Integer orderId, LoginSourceDTO loginSource);

    OrderCreateResDTO orderCreate(OrderCreateReqDTO req, LoginSourceDTO loginSource);

    OrderUpdateResDTO orderUpdate(OrderUpdateReqDTO req, LoginSourceDTO loginSource);
}
