package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.MemberNoticeEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberNoticeRepository
        extends JpaRepository<MemberNoticeEntity, Integer>,
                JpaSpecificationExecutor<MemberNoticeEntity> {

    @Query(
            """
                    SELECT m FROM MemberNoticeEntity m
                    WHERE m.member.memberId = :memberId OR upper(m.noticeTitle) LIKE upper(concat('%', :noticeTitle, '%')) OR upper(m.noticeContent) LIKE upper(concat('%', :noticeContent, '%'))
                    ORDER BY m.member.memberId""")
    Page<MemberNoticeEntity> findByMemberIdOrNoticeTitleOrNoticeContent(
            @Param("memberId") Integer memberId,
            @Param("noticeTitle") String noticeTitle,
            @Param("noticeContent") String noticeContent,
            Pageable pageable);
}
