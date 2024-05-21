package com.tibame.group1.web.service;

import com.tibame.group1.common.enums.OrderMemberIdentity;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.web.dto.*;

import java.util.List;

public interface OrderService {

    OrderResDTO orderGetById(Integer orderId, LoginSourceDTO loginSource)
            throws CheckRequestErrorException;

    List<OrderResDTO> orderGetAll(LoginSourceDTO loginSource, OrderMemberIdentity identity)
            throws CheckRequestErrorException;

    List<OrderDetailResDTO> orderDetail(Integer orderId, LoginSourceDTO loginSource)
            throws CheckRequestErrorException;

    OrderCreateResDTO orderCreate(OrderCreateReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException;

    OrderUpdateResDTO orderUpdate(OrderUpdateReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException;
}
