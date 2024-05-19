package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.MemberEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

    @Query("SELECT m FROM MemberEntity m WHERE m.memberAccount = :memberAccount")
    MemberEntity findByMemberAccount(@Param("memberAccount") String memberAccount);

    @Query("SELECT (count(m) > 0) FROM MemberEntity m WHERE m.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT m FROM MemberEntity m WHERE m.email = :email")
    MemberEntity findByEmail(@Param("email") String email);

    @Query("SELECT m FROM MemberEntity m WHERE m.cidResetVerifyUUID = :cidResetVerifyUUID")
    MemberEntity findByCidResetVerifyUUID(@Param("cidResetVerifyUUID") String cidResetVerifyUUID);

    @Query(
            """
                SELECT m FROM MemberEntity m
                WHERE m.memberId = :memberId OR m.memberAccount = :memberAccount OR m.email = :email""")
    Page<MemberEntity> findByMemberIdOrMemberAccountOrEmail(
            @Param("memberId") Integer memberId,
            @Param("memberAccount") String memberAccount,
            @Param("email") String email,
            Pageable pageable);
}
