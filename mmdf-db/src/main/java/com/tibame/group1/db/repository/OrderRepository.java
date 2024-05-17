package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.OrderEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findBySellerIdOrderByCreateTimeDesc(Integer sellerId);

    List<OrderEntity> findByBuyerIdOrderByCreateTimeDesc(Integer buyerId);

    List<OrderEntity> findBySellerIdOrBuyerIdOrderByCreateTimeDesc(Integer sellerId, Integer buyerId);
}
