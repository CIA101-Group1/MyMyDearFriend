package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.MemberEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    @Query("SELECT m FROM MemberEntity m WHERE m.memberAccount = :memberAccount")
    MemberEntity findByMemberAccount(@Param("memberAccount") String memberAccount);

    @Query("SELECT (count(m) > 0) FROM MemberEntity m WHERE m.email = :email")
    boolean existsByEmail(@Param("email") String email);
}
