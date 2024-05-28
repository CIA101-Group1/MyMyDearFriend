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

    List<OrderEntity> findBySellerIdOrBuyerIdOrderByCreateTimeDesc(Integer sellerId, Integer buyerId);

    @Query("SELECT o FROM OrderEntity o " +
            "JOIN MemberEntity b ON o.buyerId = b.memberId " +
            "JOIN MemberEntity s ON o.sellerId = s.memberId " +
            "WHERE (:orderStatus IS NULL OR o.orderStatus = :orderStatus) " +
            "AND (:buyerName IS NULL OR b.name LIKE %:buyerName%) " +
            "AND (:sellerName IS NULL OR s.name LIKE %:sellerName%) " +
            "AND (:orderId IS NULL OR o.orderId = :orderId) " +
            "ORDER BY o.createTime DESC")
    List<OrderEntity> findOrders(
            @Param("orderStatus") Byte orderStatus,
            @Param("buyerName") String buyerName,
            @Param("sellerName") String sellerName,
            @Param("orderId") Integer orderId);

}
