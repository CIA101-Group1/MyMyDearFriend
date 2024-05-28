package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.RevenueRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Set;

public interface RevenueRecordRepository extends JpaRepository<RevenueRecordEntity,Integer> {
//    @Query("SELECT m.charge_fee FROM RevenueRecordEntity m WHERE m.date LIKE %?%")
//    Optional<Integer> findDayRevenue(String time);
}
