package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.OrderEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    OrderEntity findByIdAndSellerId(Integer orderId, Integer sellerId);

    OrderEntity findByIdAndBuyerId(Integer orderId, Integer buyerId);

    List<OrderEntity> findBySellerId(Integer sellerId);

    List<OrderEntity> findByBuyerId(Integer buyerId);
}
