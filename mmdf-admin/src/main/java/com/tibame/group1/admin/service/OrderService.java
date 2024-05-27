package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.OrderDetailResDTO;
import com.tibame.group1.admin.dto.OrderResDTO;

import java.util.List;

public interface OrderService {

    List<OrderResDTO> orderGetAll();
    List<OrderDetailResDTO> orderDetail(Integer orderId);
}
