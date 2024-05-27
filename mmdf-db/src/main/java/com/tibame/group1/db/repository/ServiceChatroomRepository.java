package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.ServiceChatroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceChatroomRepository extends JpaRepository<ServiceChatroomEntity , Integer> {
    @Query("SELECT message FROM ServiceChatroomEntity WHERE customerId = ?1  ORDER BY date DESC LIMIT 1")
    String findLastMessage(Integer id);

    @Query("SELECT m FROM ServiceChatroomEntity m WHERE m.customerId = ?1 AND m.serviceId = ?2")
    List<ServiceChatroomEntity> findHistoryMessage(Integer member, Integer service);
}
