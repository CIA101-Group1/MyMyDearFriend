package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.BidEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<BidEntity, Integer> {
    List<BidEntity> findByProductIdOrderByIdDesc(Integer productId);

    // 查詢特定商品的最高出價
    Optional<BidEntity> findTopByProductIdOrderByAmountDesc(Integer productId);
}
