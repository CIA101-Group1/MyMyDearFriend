package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Integer> {

}
