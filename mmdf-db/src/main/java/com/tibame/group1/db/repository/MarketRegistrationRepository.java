package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.MarketRegistrationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketRegistrationRepository extends JpaRepository <MarketRegistrationEntity, Integer> {
    //根據會員id查詢報名的市集id
    @Query("SELECT m.memberId FROM MemberEntity m WHERE m.memberId = :memberId")
    List<Integer> findMarketIdsByMemberId(Integer memberId);


}
