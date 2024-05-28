package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.MarketEntity;
import com.tibame.group1.db.entity.MarketRegistrationEntity;
import com.tibame.group1.db.entity.MemberEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarketRegistrationRepository extends JpaRepository <MarketRegistrationEntity, MarketRegistrationEntity.MarketRegistrationId> {
    // 根據會員ID查詢所有報名紀錄
    @Query("SELECT m FROM MarketRegistrationEntity m WHERE m.memberId = :memberId")
    List<MarketRegistrationEntity> findAllByMemberId(@Param("memberId") MemberEntity memberId);

    // 根據市集ID查詢所有報名紀錄
    @Query("SELECT m FROM MarketRegistrationEntity m WHERE m.marketId = :marketId")
    List<MarketRegistrationEntity> findAllByMarketId(@Param("marketId") MarketEntity marketId);
    
}
