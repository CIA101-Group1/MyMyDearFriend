package com.tibame.group1.db.repository;

import com.tibame.group1.common.dto.web.OrderResDTO;
import com.tibame.group1.db.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @Query("SELECT o FROM OrderEntity o WHERE o.sellerId = :sellerId")
    List<OrderResDTO> findBySellerId(@Param("sellerId") Integer sellerId);
}
