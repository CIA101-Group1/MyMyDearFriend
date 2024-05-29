package com.tibame.group1.admin.service.impl;

import com.tibame.group1.admin.dto.OrderDetailResDTO;
import com.tibame.group1.admin.dto.OrderReqDTO;
import com.tibame.group1.admin.dto.OrderResDTO;
import com.tibame.group1.admin.dto.OrderUpdateReqDTO;
import com.tibame.group1.admin.service.NoticeService;
import com.tibame.group1.admin.service.OrderService;
import com.tibame.group1.common.enums.OrderStatus;
import com.tibame.group1.common.enums.WalletCategory;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.*;
import com.tibame.group1.db.repository.*;

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

    @Autowired private ProductRepository productRepository;

    @Autowired private ProductImgRepository productImgRepository;

    @Autowired private MemberRepository memberRepository;

    @Autowired private NoticeService noticeService;

    @Autowired private WalletHistoryRepository walletHistoryRepository;

    @Override
    public List<OrderResDTO> orderGetAll(OrderReqDTO req)
            throws CheckRequestErrorException {
        List<OrderResDTO> resList = new ArrayList<>();
        Byte orderstatus = null;
        Integer orderId = null;
        String buyerName = null;
        String sellerName = null;

        if (req.getOrderStatus() != OrderStatus.ALL) {
            orderstatus = req.getOrderStatus().getCode();
        }

        if(req.getOrderId() != null && !req.getOrderId().equals("null") && !req.getOrderId().isEmpty()){
            orderId = Integer.parseInt(req.getOrderId());
        }

        if(!(req.getBuyerName().isEmpty() || req.getBuyerName().equals("null"))){
            buyerName = req.getBuyerName();
        }

        if(!(req.getSellerName().isEmpty() || req.getSellerName().equals("null"))){
            sellerName = req.getSellerName();
        }

        List<OrderEntity> orderList = orderRepository.findOrders(orderstatus, buyerName, sellerName, orderId);

        for (OrderEntity order : orderList) {
            OrderResDTO res = new OrderResDTO();
            MemberEntity buyer = memberRepository.findById(order.getBuyerId()).orElse(null);
            MemberEntity seller = memberRepository.findById(order.getSellerId()).orElse(null);
            // 查詢訂單詳情
            List<OrderDetailResDTO> orderDetailList = orderDetail(order.getOrderId());
            // 將查詢資料轉換至 List<OrderResDTO>
            res.setOrderId(order.getOrderId());
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
    public List<OrderDetailResDTO> orderDetail(Integer orderId) throws CheckRequestErrorException {
        List<OrderDetailEntity> orderDetailList = orderDetailRepository.findByOrderId(orderId);
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
                log.warn("查無會員資料 {}", product.getSellerId());
                throw new CheckRequestErrorException("查無會員資料");
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
    public String orderUpdate(OrderUpdateReqDTO req) throws CheckRequestErrorException {
        OrderEntity order = orderRepository.findById(req.getOrderId()).orElse(null);
        List<OrderDetailEntity> orderDetailList = orderDetailRepository.findByOrderId(req.getOrderId());

        OrderStatus orderStatus = req.getOrderStatus();
        int statusCode = 0;
        if (orderStatus != null) {
            statusCode = orderStatus.getCode();
            // 退貨
            if(statusCode == 3){
                // 商品庫存返回
                for(OrderDetailEntity orderDetail : orderDetailList){
                    ProductEntity product = productRepository.findById(orderDetail.getProductId()).orElse(null);
                    Integer quantity = product.getQuantity() + orderDetail.getQuantity();
                    product.setQuantity(quantity);
                    productRepository.save(product);
                }
                // 買賣家退款錢包餘額計算
                MemberEntity buyer = memberRepository.findById(order.getBuyerId()).orElse(null);
                MemberEntity seller = memberRepository.findById(order.getSellerId()).orElse(null);
                Integer buyerWalletAmount = buyer.getWalletAmount();
                Integer sellerWalletAmount = seller.getWalletAmount();

                buyer.setWalletAmount(buyerWalletAmount + order.getPriceAfterDiscount());
                memberRepository.save(buyer);
                seller.setWalletAmount(
                        sellerWalletAmount - (order.getPriceBeforeDiscount() - order.getFee()));
                memberRepository.save(seller);

                // 創建買家的 WalletHistoryEntity
                WalletHistoryEntity buyerWalletHistory = new WalletHistoryEntity();
                buyerWalletHistory.setMember(buyer); // 設置會員ID
                buyerWalletHistory.setChangeAmount(order.getPriceAfterDiscount());
                buyerWalletHistory.setChangeTime(new Date());
                buyerWalletHistory.setChangeType(WalletCategory.REFUND);
                walletHistoryRepository.save(buyerWalletHistory);

                // 創建賣家的 WalletHistoryEntity
                WalletHistoryEntity sellerWalletHistory = new WalletHistoryEntity();
                sellerWalletHistory.setMember(seller); // 設置會員ID
                sellerWalletHistory.setChangeAmount(order.getPriceBeforeDiscount() - order.getFee());
                sellerWalletHistory.setChangeTime(new Date());
                sellerWalletHistory.setChangeType(WalletCategory.REFUND);
                walletHistoryRepository.save(sellerWalletHistory);
            }
        } else {
            log.warn("選擇訂單狀態錯誤");
            throw new CheckRequestErrorException("選擇訂單狀態錯誤");
        }

        order.setOrderStatus((byte) statusCode);
        orderRepository.save(order);

        String message = "您的訂單編號: " + order.getOrderId() + " ，訂單狀態: " + orderStatus.getMessage();
        MemberEntity buyer = memberRepository.findById(order.getBuyerId()).orElse(null);
        noticeService.memberNoticeCreate(
                buyer, MemberNoticeEntity.NoticeCategory.GENERAL_PRODUCT, "訂單狀態更新", message, true);
        MemberEntity seller = memberRepository.findById(order.getSellerId()).orElse(null);
        noticeService.memberNoticeCreate(
                seller, MemberNoticeEntity.NoticeCategory.GENERAL_PRODUCT, "訂單狀態更新", message, true);

        return "success";
    }
}
