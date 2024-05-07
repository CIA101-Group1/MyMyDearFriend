package com.tibame.group1.web.service.impl;

import com.tibame.group1.db.entity.OrderEntity;
import com.tibame.group1.db.repository.OrderRepository;
import com.tibame.group1.web.dto.OrderCreateReqDTO;
import com.tibame.group1.web.dto.OrderCreateResDTO;
import com.tibame.group1.web.service.OrderService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional(rollbackOn = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Autowired private OrderRepository orderRepository;

    @Override
    public OrderCreateResDTO orderCreate(OrderCreateReqDTO req) {
        OrderEntity order = new OrderEntity();
        order.setBuyerId(req.getBuyerId());
        order.setSellerId(req.getSellerId());
        order.setMemberCouponId1(req.getMemberCouponId1());
        order.setMemberCouponId2(req.getMemberCouponId2());
        order.setPriceBeforeDiscount(req.getPriceBeforeDiscount());
        order.setDiscount(req.getDiscount());
        order.setPriceAfterDiscount(req.getPriceAfterDiscount());
        order.setCreateTime(new Date());
        order.setOrderStatus((byte) 0);
        order.setName(req.getName());
        order.setPhone(req.getPhone());
        order.setAddress(req.getAddress());
        order.setFee((int) (req.getPriceBeforeDiscount() * 0.03));
        order = orderRepository.save(order);
        OrderCreateResDTO resDTO = new OrderCreateResDTO();
        resDTO.setOrderId(order.getOrderId());
        return resDTO;
    }
}
