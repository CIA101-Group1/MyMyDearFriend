package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query("SELECT p FROM ProductEntity p WHERE p.categoryId = :categoryId")
    List<ProductEntity> findAllByCategoryId(@Param("categoryId") Integer categoryId);
}
