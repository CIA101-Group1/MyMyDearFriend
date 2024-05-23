package com.tibame.group1.db.repository;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tibame.group1.db.entity.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {
    @Query("SELECT m FROM MessageEntity m WHERE roomId = ?1")
    ArrayList<MessageEntity> findByRoomId(Integer roomId);
    
    @Query("SELECT (count(m) > 0) FROM MessageEntity m WHERE roomId = ?1 AND m.date = ?2")
    boolean FindIsData(Integer roomId, Timestamp date);
}
