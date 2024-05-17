package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.enums.OrderMemberStatus;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.OrderDetailEntity;
import com.tibame.group1.db.entity.OrderEntity;
import com.tibame.group1.db.entity.ProductTestEntity;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.db.repository.OrderDetailRepository;
import com.tibame.group1.db.repository.OrderRepository;
import com.tibame.group1.db.repository.ProductTestRepository;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.OrderService;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired private OrderRepository orderRepository;

    @Autowired private OrderDetailRepository orderDetailRepository;

    @Autowired private MemberRepository memberRepository;

    @Autowired private ProductTestRepository productRepository;

    @Override
    public OrderCreateResDTO orderCreate(OrderCreateReqDTO req, LoginSourceDTO loginSource) {
        OrderCreateResDTO resDTO = new OrderCreateResDTO();

        // 檢查 user 是否存在
        MemberEntity buyer = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (buyer == null) {
            log.warn("user {} 不存在", loginSource.getMemberId());

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        OrderEntity order = new OrderEntity();

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
//            ProductEntity product = productRepository.findById(buyProduct.getProductId()).orElse(null);
//
//            // 檢查 product 是否存在，庫存是否足夠
//            if (product == null) {
//                log.warn("商品 {} 不存在", buyProduct.getProductId());
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//            } else if (product.getQuantity() < buyProduct.getQuantity()) {
//                log.warn("商品 {} 庫存量不足，無法購買。剩餘庫存 {} ，欲購買數量 {}",
//                        buyProduct.getProductId(),
//                        product.getQuantity(),
//                        buyProduct.getQuantity());
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//            }
//
//            // 扣除商品庫存
//            productRepository.updateQuantity(product.getProductId(), product.getQuantity - buyProduct.getQuantity());

            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.setOrderId(order.getId());
            orderDetail.setProductId(buyProduct.getProductId());
            orderDetail.setQuantity(buyProduct.getQuantity());
            orderDetail.setPrice(buyProduct.getPrice());
            orderDetailRepository.save(orderDetail);
        }


        resDTO.setOrderId(order.getId());
        return resDTO;
    }

    @Override
    public List<OrderResDTO> orderGetAll(LoginSourceDTO loginSource, OrderMemberStatus status) {
        List<OrderEntity> orderList = new ArrayList<>();
        List<OrderResDTO> resList = new ArrayList<>();

        switch (status.name()){

            // 取得全部訂單
            case "NONE" :
                // 檢查 user 是否存在
                MemberEntity user = memberRepository.findById(loginSource.getMemberId()).orElse(null);
                if (user == null) {
                    log.warn("user {} 不存在", loginSource.getMemberId());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                // 查詢訂單，檢查訂單是否存在
                orderList = orderRepository.findAll();
                if (orderList.isEmpty()) {
                    log.warn("查無訂單");
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                break;

            // 取得 Buyer 全部訂單
            case "BUYER" :
                // 檢查買家是否存在
                MemberEntity buyer = memberRepository.findById(loginSource.getMemberId()).orElse(null);
                if (buyer == null) {
                    log.warn("買家 {} 不存在", loginSource.getMemberId());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                // 查詢訂單，檢查訂單是否存在
                orderList = orderRepository.findByBuyerId(buyer.getMemberId());
                if (orderList.isEmpty()) {
                    log.warn("查無訂單");
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                break;

            // 取得 Seller 全部訂單
            case "SELLER" :
                // 檢查賣家是否存在
                MemberEntity seller = memberRepository.findById(loginSource.getMemberId()).orElse(null);
                if (seller == null) {
                    log.warn("賣家 {} 不存在", loginSource.getMemberId());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                // 查詢訂單，檢查訂單是否存在
                orderList = orderRepository.findBySellerId(seller.getMemberId());
                if (orderList.isEmpty()) {
                    log.warn("查無訂單");
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                }
                break;
        }

        for(OrderEntity order : orderList){
            OrderResDTO res = new OrderResDTO();
            // 查詢訂單詳情
            List<OrderDetailResDTO> orderDetailList =  orderDetail(order.getId(), loginSource);
            // 將查詢資料轉換至 List<OrderResDTO>
            res.setOrderId(order.getId());
            res.setBuyerId(order.getBuyerId());
            res.setSellerId(order.getSellerId());
            res.setMemberCouponId1(order.getMemberCouponId1());
            res.setMemberCouponId2(order.getMemberCouponId2());
            res.setPriceBeforeDiscount(order.getPriceBeforeDiscount());
            res.setDiscount(order.getDiscount());
            res.setPriceAfterDiscount(order.getPriceAfterDiscount());
            res.setCreateTime(order.getCreateTime());
            res.setOrderStatus(order.getOrderStatus());
            res.setName(order.getName());
            res.setPhone(order.getPhone());
            res.setAddress(order.getAddress());
            res.setFee(order.getFee());
            res.setOrderDetailList(orderDetailList);
            resList.add(res);
        }
        return resList;
    }

    @Override
    public OrderResDTO orderBuyerGetById(Integer orderId, LoginSourceDTO loginSource, OrderMemberStatus status) {
        OrderEntity order = null;
        OrderResDTO res = new OrderResDTO();

        switch (status){
            case NONE :
                // 檢查 user 是否存在
                MemberEntity user = memberRepository.findById(loginSource.getMemberId()).orElse(null);
                if (user == null) {
                    log.warn("user {} 不存在", loginSource.getMemberId());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                // 查詢訂單，檢查訂單是否存在
                order = orderRepository.findByIdAndBuyerId(orderId, user.getMemberId());
                if (order == null) {
                    log.warn("訂單 {} 不存在", orderId);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                break;

            case BUYER :
                // 檢查買家是否存在
                MemberEntity buyer = memberRepository.findById(loginSource.getMemberId()).orElse(null);
                if (buyer == null) {
                    log.warn("買家 {} 不存在", loginSource.getMemberId());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                // 查詢訂單，檢查訂單是否存在
                order = orderRepository.findByIdAndBuyerId(orderId, buyer.getMemberId());
                if (order == null) {
                    log.warn("訂單 {} 不存在", orderId);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                break;

            case SELLER:
                // 檢查賣家是否存在
                MemberEntity seller = memberRepository.findById(loginSource.getMemberId()).orElse(null);
                if (seller == null) {
                    log.warn("買家 {} 不存在", loginSource.getMemberId());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                // 查詢訂單，檢查訂單是否存在
                order = orderRepository.findByIdAndSellerId(orderId, seller.getMemberId());
                if (order == null) {
                    log.warn("訂單 {} 不存在", orderId);
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }
                break;
        }
        // 查詢訂單詳情
        List<OrderDetailResDTO> orderDetailList =  orderDetail(orderId, loginSource);

        // 將查詢資料轉換至 OrderResDTO
        res.setOrderId(order.getId());
        res.setBuyerId(order.getBuyerId());
        res.setSellerId(order.getSellerId());
        res.setMemberCouponId1(order.getMemberCouponId1());
        res.setMemberCouponId2(order.getMemberCouponId2());
        res.setPriceBeforeDiscount(order.getPriceBeforeDiscount());
        res.setDiscount(order.getDiscount());
        res.setPriceAfterDiscount(order.getPriceAfterDiscount());
        res.setCreateTime(order.getCreateTime());
        res.setOrderStatus(order.getOrderStatus());
        res.setName(order.getName());
        res.setPhone(order.getPhone());
        res.setAddress(order.getAddress());
        res.setFee(order.getFee());
        res.setOrderDetailList(orderDetailList);

        return res;
    }

    @Override
    public List<OrderDetailResDTO> orderDetail(Integer orderId, LoginSourceDTO loginSource) {
        // 檢查 user 是否存在
        MemberEntity user = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (user == null) {
            log.warn("user {} 不存在", loginSource.getMemberId());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 查詢訂單，檢查訂單是否存在
        OrderEntity order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            log.warn("訂單 {} 不存在", orderId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 查詢訂單詳情
        List<OrderDetailEntity> orderDetailList = orderDetailRepository.findByOrderId(orderId);

        if(orderDetailList.isEmpty()){
            log.warn("訂單詳情 {} 不存在", orderId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 將查詢資料轉換至 List<OrderDetailResDTO>
        List<OrderDetailResDTO> list = new ArrayList<>();

        for (OrderDetailEntity orderDetail : orderDetailList) {

            // 檢查商品是否存在
            ProductTestEntity product = productRepository.findById(orderDetail.getProductId()).orElse(null);
            if(product == null){
                log.warn("商品 {} 不存在", orderDetail.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 檢查賣家是否存在
            MemberEntity seller = memberRepository.findById(product.getSellerId()).orElse(null);
            if(seller == null){
                log.warn("賣家 {} 不存在", product.getSellerId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            OrderDetailResDTO res = new OrderDetailResDTO();
            res.setOrderId(orderDetail.getOrderId());
            res.setProductId(orderDetail.getProductId());
            res.setQuantity(orderDetail.getQuantity());
            res.setPrice(orderDetail.getPrice());
            res.setName(product.getName());
            res.setSeller(seller.getName());
            list.add(res);
        }
        return list;
    }

    @Override
    public OrderUpdateResDTO orderUpdate(OrderUpdateReqDTO req, LoginSourceDTO loginSource) {
        MemberEntity member = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (member != null) {
            OrderEntity order = orderRepository.findById(req.getOrderId()).orElse(null);
            if (order != null) {
                order.setOrderStatus(req.getOrderStatus());
                order = orderRepository.save(order);

                OrderUpdateResDTO resDTO = new OrderUpdateResDTO();
                resDTO.setOrderId(order.getId());
                return resDTO;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
