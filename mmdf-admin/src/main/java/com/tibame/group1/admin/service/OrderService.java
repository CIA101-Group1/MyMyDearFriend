package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.OrderDetailResDTO;
import com.tibame.group1.admin.dto.OrderReqDTO;
import com.tibame.group1.admin.dto.OrderResDTO;
import com.tibame.group1.admin.dto.OrderUpdateReqDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;

import java.util.List;

public interface OrderService {

    List<OrderResDTO> orderGetAll(OrderReqDTO req) throws CheckRequestErrorException;

    List<OrderDetailResDTO> orderDetail(Integer orderId) throws CheckRequestErrorException;

    String orderUpdate(OrderUpdateReqDTO req) throws CheckRequestErrorException;
}
