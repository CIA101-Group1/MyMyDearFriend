package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

}
