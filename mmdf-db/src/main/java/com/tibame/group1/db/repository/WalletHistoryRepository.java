package com.tibame.group1.db.repository;


import com.tibame.group1.db.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletHistoryRepository extends JpaRepository<CouponEntity, Integer>{

}
