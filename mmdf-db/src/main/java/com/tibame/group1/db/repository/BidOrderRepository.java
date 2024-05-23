package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.BidOrderEntity;

import com.tibame.group1.db.entity.BidProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidOrderRepository extends JpaRepository<BidOrderEntity, Integer> {
    List<BidOrderEntity> findAllBySellerId(Integer sellerId);
    List<BidOrderEntity> findAllByBuyerId(Integer buyerId);
}
