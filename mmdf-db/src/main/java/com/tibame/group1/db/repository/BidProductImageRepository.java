package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.BidProductImageEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BidProductImageRepository extends JpaRepository<BidProductImageEntity, Integer> {

    void deleteByProductId(Integer productId);
}
