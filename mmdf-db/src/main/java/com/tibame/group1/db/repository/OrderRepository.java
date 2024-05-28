package com.tibame.group1.db.repository;

import com.tibame.group1.common.enums.OrderStatus;
import com.tibame.group1.db.entity.OrderEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findBySellerIdOrderByCreateTimeDesc(Integer sellerId);

    List<OrderEntity> findByBuyerIdOrderByCreateTimeDesc(Integer buyerId);

    List<OrderEntity> findBySellerIdOrBuyerIdOrderByCreateTimeDesc(
            Integer sellerId, Integer buyerId);

    @Query(
            "SELECT o FROM OrderEntity o "
                    + "WHERE (:orderStatus IS NULL OR o.orderStatus = :orderStatus) "
                    + "AND (:orderId IS NULL OR o.Id = :orderId) "
                    + "AND (:sellerId IS NULL OR o.sellerId = :sellerId) "
                    + "AND (:buyerId IS NULL OR o.buyerId = :buyerId) "
                    + "ORDER BY o.createTime DESC")
    List<OrderEntity> findAllByOrderByCreateTimeDesc(
            @Param("orderStatus") Byte orderStatus,
            @Param("orderId") Integer orderId,
            @Param("buyerId") Integer buyerId,
            @Param("sellerId") Integer sellerId);
}
