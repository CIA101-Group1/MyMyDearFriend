package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CouponRepository extends JpaRepository<CouponEntity, Integer> {

    @Query("SELECT c FROM CouponEntity c WHERE c.couponID = :couponID")
    CouponEntity findByCouponID(@Param("couponID") String couponID);
}