package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.CannedMessageEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CannedMessageRepository extends JpaRepository<CannedMessageEntity,Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM CannedMessageEntity cm WHERE cm.serviceId = ?1 AND cm.message LIKE ?2")
    void deleteByServiceIdAndMessage(Integer serviceId, String message);

    @Query("SELECT cm.message FROM CannedMessageEntity cm WHERE cm.serviceId = ?1")
    List<String> getMessageByServiceId(Integer serviceId);
}
