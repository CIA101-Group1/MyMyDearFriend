package com.tibame.group1.web.service.impl;

import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.OrderDetailEntity;
import com.tibame.group1.db.entity.OrderEntity;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.db.repository.OrderDetailRepository;
import com.tibame.group1.db.repository.OrderRepository;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.OrderService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderDetailRepository orderDetailRepository;
    @Autowired private MemberRepository memberRepository;

    @Override
    public OrderCreateResDTO orderCreate(OrderCreateReqDTO req, LoginSourceDTO loginSource) {
        OrderEntity order = new OrderEntity();
        MemberEntity buyer = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (buyer != null) {
            order.setBuyerId(buyer.getMemberId());
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

            for (OrderBuyProductDTO buyProduct : req.getBuyProductList()) {
                OrderDetailEntity orderDetail = new OrderDetailEntity();
                orderDetail.setOrderId(order.getOrderId());
                orderDetail.setProductId(buyProduct.getProductId());
                orderDetail.setQuantity(buyProduct.getQuantity());
                orderDetail.setPrice(buyProduct.getPrice());
                orderDetailRepository.save(orderDetail);
            }

            OrderCreateResDTO resDTO = new OrderCreateResDTO();
            resDTO.setOrderId(order.getOrderId());
            return resDTO;
        } else {
            return null;
        }
    }

    @Override
    public List<OrderEntity> orderSellerGetAll(LoginSourceDTO loginSource) {
        MemberEntity seller = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (seller != null) {
            return orderRepository.findBySellerId(seller.getMemberId());
        } else {
            return null;
        }
    }

    @Override
    public List<OrderEntity> orderBuyerGetAll(LoginSourceDTO loginSource) {
        MemberEntity buyer = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (buyer != null) {
            return orderRepository.findByBuyerId(buyer.getMemberId());
        } else {
            return null;
        }
    }

    @Override
    public List<OrderDetailEntity> orderDetail(Integer orderId) {

        return orderDetailRepository.findByOrderId(orderId);
    }
}
