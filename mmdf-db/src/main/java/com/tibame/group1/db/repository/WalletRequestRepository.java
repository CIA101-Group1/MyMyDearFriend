package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.WalletRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WalletRequestRepository extends JpaRepository<WalletRequestEntity, Integer> {
    @Query("select w from WalletRequestEntity w where w.member.memberId = :memberId")
    WalletRequestEntity findByMember_MemberId(@Param("memberId") Integer memberId);
}
