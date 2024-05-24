package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.enums.OrderMemberIdentity;
import com.tibame.group1.common.enums.OrderStatus;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.*;
import com.tibame.group1.db.repository.*;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.NoticeService;
import com.tibame.group1.web.service.OrderService;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired private OrderRepository orderRepository;

    @Autowired private OrderDetailRepository orderDetailRepository;

    @Autowired private MemberRepository memberRepository;

    @Autowired private ProductRepository productRepository;

    @Autowired private ProductImgRepository productImgRepository;

    @Autowired private NoticeService noticeService;

    @Override
    public OrderCreateResDTO orderCreate(OrderCreateReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        // 檢查會員是否存在
        MemberEntity buyer = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (buyer == null) {
            log.warn("查無會員資料 {}", loginSource.getMemberId());
            throw new CheckRequestErrorException("查無會員資料");
        }
        MemberEntity seller = memberRepository.findById(req.getSellerId()).orElse(null);
        if (seller == null) {
            log.warn("查無會員資料 {}", loginSource.getMemberId());
            throw new CheckRequestErrorException("查無會員資料");
        }
        // 建立訂單
        OrderEntity order = new OrderEntity();
        order.setBuyerId(buyer.getMemberId());
        order.setSellerId(req.getSellerId());
        order.setMemberCouponId1(req.getMemberCouponId1());
        order.setMemberCouponId2(req.getMemberCouponId2());
        order.setPriceBeforeDiscount(req.getPriceBeforeDiscount());
        order.setDiscount(req.getDiscount());
        order.setPriceAfterDiscount(req.getPriceAfterDiscount());
        order.setCreateTime(new Date());
        order.setOrderStatus(OrderStatus.TO_SHIP.getCode());
        order.setName(req.getName());
        order.setPhone(req.getPhone());
        order.setAddress(req.getAddress());
        order.setFee((int) (req.getPriceBeforeDiscount() * 0.03));
        order = orderRepository.save(order);

        // 買賣家錢包餘額計算
        if (buyer.getWalletAmount() < order.getPriceAfterDiscount()){
            log.warn("買家錢包餘額不足");
            throw new CheckRequestErrorException("錢包餘額不足，請先儲值");
        }else{
            buyer.setWalletAmount(buyer.getWalletAmount() - order.getPriceAfterDiscount());
            memberRepository.save(buyer);
        }

        seller.setWalletAmount(seller.getWalletAmount() + order.getPriceBeforeDiscount() - order.getFee());
        memberRepository.save(seller);

        // 建立訂單詳情
        for (OrderBuyProductDTO buyProduct : req.getBuyProductList()) {
            // 檢查 product 是否存在，庫存是否足夠
            ProductEntity product =
                    productRepository.findById(buyProduct.getProductId()).orElse(null);
            if (product == null) {
                log.warn("查無商品 {}", buyProduct.getProductId());
                throw new CheckRequestErrorException("查無商品");
            } else if (product.getQuantity() < buyProduct.getQuantity()) {
                log.warn(
                        "商品 {} 庫存量不足，無法購買。剩餘庫存 {} ，欲購買數量 {}",
                        buyProduct.getProductId(),
                        product.getQuantity(),
                        buyProduct.getQuantity());
                throw new CheckRequestErrorException("商品庫存量不足，無法購買。"+ product.getName() +" 剩餘庫存: " + product.getQuantity());
            }

            // 扣除商品庫存
            product.setQuantity(product.getQuantity() - buyProduct.getQuantity());
            productRepository.save(product);

            OrderDetailEntity orderDetail = new OrderDetailEntity();
            orderDetail.setOrderId(order.getId());
            orderDetail.setProductId(buyProduct.getProductId());
            orderDetail.setQuantity(buyProduct.getQuantity());
            orderDetail.setPrice(buyProduct.getPrice());
            orderDetailRepository.save(orderDetail);
        }
        noticeService.memberNoticeCreate(
                buyer, MemberNoticeEntity.NoticeCategory.GENERAL_PRODUCT, "訂單已成立", "恭喜訂單新增成功! 您的訂單編號: "+ order.getId(), true);
        OrderCreateResDTO resDTO = new OrderCreateResDTO();
        resDTO.setOrderId(order.getId());
        return resDTO;
    }

    @Override
    public List<OrderResDTO> orderGetAll(LoginSourceDTO loginSource, OrderMemberIdentity identity)
            throws CheckRequestErrorException {
        List<OrderEntity> orderList = new ArrayList<>();
        List<OrderResDTO> resList = new ArrayList<>();
        // 檢查 user 是否存在
        MemberEntity user = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (user == null) {
            log.warn("查無會員資料 {}", loginSource.getMemberId());
            throw new CheckRequestErrorException("查無會員資料");
        }

        // 判斷使用者身分
        switch (identity.name()) {
                // 取得全部訂單
            case "ALL":
                // 查詢訂單，檢查訂單是否存在
                orderList = orderRepository.findAll();
                if (orderList.isEmpty()) {
                    log.warn("查無訂單");
                    throw new CheckRequestErrorException("查無訂單");
                }
                break;

                // 取得使用者全部訂單
            case "BOTH":
                // 查詢訂單，檢查訂單是否存在
                orderList =
                        orderRepository.findBySellerIdOrBuyerIdOrderByCreateTimeDesc(
                                user.getMemberId(), user.getMemberId());
                if (orderList.isEmpty()) {
                    log.warn("查無訂單");
                    throw new CheckRequestErrorException("查無訂單");
                }
                break;

                // 取得 Buyer 全部訂單
            case "BUYER":
                // 查詢訂單，檢查訂單是否存在
                orderList = orderRepository.findByBuyerIdOrderByCreateTimeDesc(user.getMemberId());
                if (orderList.isEmpty()) {
                    log.warn("查無訂單");
                    throw new CheckRequestErrorException("查無訂單");
                }
                break;

                // 取得 Seller 全部訂單
            case "SELLER":
                // 查詢訂單，檢查訂單是否存在
                orderList = orderRepository.findBySellerIdOrderByCreateTimeDesc(user.getMemberId());
                if (orderList.isEmpty()) {
                    log.warn("查無訂單");
                    throw new CheckRequestErrorException("查無訂單");
                }
                break;
        }

        for (OrderEntity order : orderList) {
            OrderResDTO res = new OrderResDTO();
            MemberEntity buyer = memberRepository.findById(order.getBuyerId()).orElse(null);
            if (buyer == null) {
                log.warn("查無會員資料 {}", loginSource.getMemberId());
                throw new CheckRequestErrorException("查無會員資料");
            }
            MemberEntity seller = memberRepository.findById(order.getSellerId()).orElse(null);
            if (seller == null) {
                log.warn("查無會員資料 {}", loginSource.getMemberId());
                throw new CheckRequestErrorException("查無會員資料");
            }
            // 查詢訂單詳情
            List<OrderDetailResDTO> orderDetailList = orderDetail(order.getId(), loginSource);
            // 將查詢資料轉換至 List<OrderResDTO>
            res.setOrderId(order.getId());
            res.setBuyerId(order.getBuyerId());
            res.setSellerId(order.getSellerId());
            res.setBuyerName(buyer.getName());
            res.setSellerName(seller.getName());
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
    public OrderResDTO orderGetById(Integer orderId, LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        // 檢查 user 是否存在
        MemberEntity user = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (user == null) {
            log.warn("查無會員資料 {}", loginSource.getMemberId());
            throw new CheckRequestErrorException("查無會員資料");
        }
        // 查詢訂單，檢查訂單是否存在
        OrderEntity order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            log.warn("查無訂單 {}", orderId);
            throw new CheckRequestErrorException("查無訂單");
        }

        // 查詢訂單詳情
        List<OrderDetailResDTO> orderDetailList = orderDetail(orderId, loginSource);

        // 將查詢資料轉換至 OrderResDTO
        OrderResDTO res = new OrderResDTO();
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
    public List<OrderDetailResDTO> orderDetail(Integer orderId, LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        // 檢查 user 是否存在
        MemberEntity user = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (user == null) {
            log.warn("查無會員資料 {}", loginSource.getMemberId());
            throw new CheckRequestErrorException("查無會員資料");
        }
        // 查詢訂單，檢查訂單是否存在
        OrderEntity order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            log.warn("查無訂單 {}", orderId);
            throw new CheckRequestErrorException("查無訂單");
        }
        // 查詢訂單詳情，檢查訂單詳情是否存在
        List<OrderDetailEntity> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailList.isEmpty()) {
            log.warn("查無訂單詳情 {}", orderId);
            throw new CheckRequestErrorException("查無訂單詳情");
        }

        // 將查詢資料轉換至 List<OrderDetailResDTO>
        List<OrderDetailResDTO> list = new ArrayList<>();
        for (OrderDetailEntity orderDetail : orderDetailList) {
            // 檢查商品是否存在
            ProductEntity product =
                    productRepository.findById(orderDetail.getProductId()).orElse(null);
            if (product == null) {
                log.warn("查無商品 {}", orderDetail.getProductId());
                throw new CheckRequestErrorException("查無商品");
            }
            // 檢查賣家是否存在
            MemberEntity seller = memberRepository.findById(product.getSellerId()).orElse(null);
            if (seller == null) {
                log.warn("查無賣家 {}", product.getSellerId());
                throw new CheckRequestErrorException("查無賣家");
            }
            OrderDetailResDTO res = new OrderDetailResDTO();
            res.setOrderId(orderDetail.getOrderId());
            res.setProductId(orderDetail.getProductId());
            res.setQuantity(orderDetail.getQuantity());
            res.setPrice(orderDetail.getPrice());
            res.setName(product.getName());
            res.setSeller(seller.getName());
            // 查詢商品圖片
            List<ProductImgEntity> images =
                    productImgRepository.findByProductEntity_ProductId(product.getProductId());
            if (!images.isEmpty()) {
                ProductImgEntity image = images.get(0);
                String imageBase64 = Base64.getEncoder().encodeToString(image.getImage());
                res.setImageBase64(imageBase64);
            } else {
                res.setImageBase64(null);
            }
            list.add(res);
        }
        return list;
    }

    @Override
    public OrderUpdateResDTO orderUpdate(OrderUpdateReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        // 檢查 user 是否存在
        MemberEntity user = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (user == null) {
            log.warn("查無會員資料 {}", loginSource.getMemberId());
            throw new CheckRequestErrorException("查無會員資料");
        }
        // 查詢訂單，檢查訂單是否存在
        OrderEntity order = orderRepository.findById(req.getOrderId()).orElse(null);
        if (order == null) {
            log.warn("查無訂單 {}", req.getOrderId());
            throw new CheckRequestErrorException("查無訂單");
        }
        // 更新訂單狀態
        order.setOrderStatus(req.getOrderStatus().getCode());
        order = orderRepository.save(order);

        OrderUpdateResDTO resDTO = new OrderUpdateResDTO();
        resDTO.setOrderId(order.getId());
        return resDTO;
    }
}
