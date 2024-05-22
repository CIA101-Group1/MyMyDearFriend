package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.MemberNoticeEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberNoticeRepository
        extends JpaRepository<MemberNoticeEntity, Integer>,
                JpaSpecificationExecutor<MemberNoticeEntity> {}
