package com.tibame.group1.db.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tibame.group1.db.entity.ChatroomEntity;

public interface ChatroomRepository extends JpaRepository<ChatroomEntity,Integer> {
    @Query("SELECT cr FROM ChatroomEntity cr WHERE cr.userA = :memberId OR cr.userB = :memberId")
    ArrayList<ChatroomEntity> findMemberAllRooms(@Param("memberId") Integer memberId);
    
    @Query("SELECT cr FROM ChatroomEntity cr WHERE cr.userA = ?1 OR cr.userB = ?1")
    List<ChatroomEntity> findMemberFriends(Integer memberId);
    
    @Query("SELECT cr.chatroomId FROM ChatroomEntity cr "
        + "WHERE (cr.userA = ?1 AND cr.userB = ?2)"
        + "OR (cr.userA = ?2 AND cr.userB = ?1) ")
    int findByRoom(Integer userA, Integer userB);
}
