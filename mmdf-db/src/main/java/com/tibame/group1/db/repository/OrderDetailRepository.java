package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.OrderDetailEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {

    List<OrderDetailEntity> findByOrderId(Integer orderId);
}
