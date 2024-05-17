package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.OrderDetailEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {

//    @Query("SELECT od.orderId, od.quantity, od.price, p.name, p.sellerId FROM OrderDetailEntity od JOIN ProductEntity p ON od.productId = p.productId WHERE od.orderId = :orderId")
//    List<OrderDetailEntity> findByOrderId(@Param("orderId") Integer orderId);

    List<OrderDetailEntity> findByOrderId(Integer orderId);
}
