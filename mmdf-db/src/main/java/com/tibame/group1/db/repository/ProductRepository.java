package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

}
