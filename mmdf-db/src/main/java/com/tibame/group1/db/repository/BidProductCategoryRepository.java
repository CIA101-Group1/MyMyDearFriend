package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.BidProductCategoryEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BidProductCategoryRepository
        extends JpaRepository<BidProductCategoryEntity, Integer> {}
