package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface CouponRepository extends JpaRepository<CouponEntity, Integer> {

}