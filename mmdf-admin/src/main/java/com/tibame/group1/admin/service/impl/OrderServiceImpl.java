package com.tibame.group1.admin.service.impl;

import com.tibame.group1.admin.dto.OrderDetailResDTO;
import com.tibame.group1.admin.dto.OrderResDTO;
import com.tibame.group1.admin.service.OrderService;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.*;
import com.tibame.group1.db.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImgRepository productImgRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<OrderResDTO> orderGetAll() {
        List<OrderEntity> orderList = new ArrayList<>();
        List<OrderResDTO> resList = new ArrayList<>();

        orderList = orderRepository.findAll();

        for(OrderEntity order : orderList){
            OrderResDTO res = new OrderResDTO();
            MemberEntity buyer = memberRepository.findById(order.getBuyerId()).orElse(null);
            MemberEntity seller = memberRepository.findById(order.getSellerId()).orElse(null);
            // 查詢訂單詳情
            List<OrderDetailResDTO> orderDetailList = orderDetail(order.getId());
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
    public List<OrderDetailResDTO> orderDetail(Integer orderId) {
        List<OrderDetailEntity> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        // 將查詢資料轉換至 List<OrderDetailResDTO>
        List<OrderDetailResDTO> list = new ArrayList<>();
        for (OrderDetailEntity orderDetail : orderDetailList) {
            // 檢查商品是否存在
            ProductEntity product = productRepository.findById(orderDetail.getProductId()).orElse(null);
            // 檢查賣家是否存在
            MemberEntity seller = memberRepository.findById(product.getSellerId()).orElse(null);

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
}
