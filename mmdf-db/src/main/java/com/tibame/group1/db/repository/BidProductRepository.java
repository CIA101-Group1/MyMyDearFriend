package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.BidProductEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface BidProductRepository extends JpaRepository<BidProductEntity, Integer> {

    List<BidProductEntity> findAllBySellerId(Integer sellerId);

    @Query(
            "SELECT bp FROM BidProductEntity bp "
                    + "WHERE (:categoryId IS NULL OR bp.categoryId = :categoryId) "
                    + "AND (:conditionId IS NULL OR bp.conditionId = :conditionId) "
                    + "AND (:name IS NULL OR bp.name LIKE %:name%) "
                    + "AND (:status IS NULL OR bp.status IN (:status)) "
                    + "ORDER BY bp.productId")
    List<BidProductEntity> findByCompositeQuery(
            @Param("categoryId") Integer categoryId,
            @Param("conditionId") Integer conditionId,
            @Param("name") String name,
            @Param("status") List<Integer> status);

    List<BidProductEntity> findAllByStatus(Integer status);

    List<BidProductEntity> findByEndTimeBeforeAndStatus(Timestamp endTime, Integer status);

    @Query(
            "SELECT DISTINCT bp FROM BidProductEntity bp "
                    + "JOIN BidEntity b ON bp.productId = b.productId "
                    + "WHERE b.memberId = :memberId")
    List<BidProductEntity> findAllBidProductForMember(@Param("memberId") Integer memberId);
}
