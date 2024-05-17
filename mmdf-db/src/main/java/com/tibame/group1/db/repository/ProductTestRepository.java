package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.ProductTestEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTestRepository extends JpaRepository<ProductTestEntity, Integer> {
    
}