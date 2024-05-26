package com.tibame.group1.web.service;

import java.util.List;

import com.tibame.group1.web.dto.ChatroomIdDTO;

public interface ChatroomService {
    public ChatroomIdDTO addRoom(Integer userA, Integer userB);
    
    public ChatroomIdDTO getRoomId(Integer userA, Integer userB);
    
    List<ChatroomIdDTO> getAllRooms(Integer memberId);
}
