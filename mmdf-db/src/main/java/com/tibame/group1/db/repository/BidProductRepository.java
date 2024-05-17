package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.BidProductEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BidProductRepository extends JpaRepository<BidProductEntity, Integer> {

    List<BidProductEntity> findAllBySellerId(Integer sellerId);

    @Query(
            "SELECT bp FROM BidProductEntity bp "
                    + "WHERE (:categoryId IS NULL OR bp.categoryId = :categoryId) "
                    + "AND (:name IS NULL OR bp.name LIKE %:name%) "
                    + "AND (:status IS NULL OR bp.status = :status) "
                    + "ORDER BY bp.productId")
    List<BidProductEntity> findByCompositeQuery(
            @Param("categoryId") Integer categoryId,
            @Param("name") String name,
            @Param("status") Integer status);
}