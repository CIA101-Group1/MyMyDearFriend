package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.OrderEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findBySellerIdOrderByCreateTimeDesc(Integer sellerId);

    List<OrderEntity> findByBuyerIdOrderByCreateTimeDesc(Integer buyerId);

    List<OrderEntity> findBySellerIdOrBuyerIdOrderByCreateTimeDesc(Integer sellerId, Integer buyerId);

//    @Query("SELECT o FROM OrderEntity o WHERE o.sellerId = :sellerId AND (:orderStatus IS NULL OR o.orderStatus = :orderStatus)")
//    List<OrderEntity> findBySellerIdAndOrderStatus(@Param("sellerId") Integer sellerId,@Param("orderStatus")Integer orderStatus);
}
