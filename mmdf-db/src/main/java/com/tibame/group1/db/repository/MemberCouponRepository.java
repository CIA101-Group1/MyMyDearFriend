package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.MemberCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberCouponRepository extends JpaRepository<MemberCouponEntity, Integer> {
    @Query("SELECT m FROM MemberCouponEntity m WHERE m.memberID = :memberID")
    List<MemberCouponEntity> getAllMemberCouponByMemberId(@Param("memberID") Integer memberID);
}
