package com.tibame.group1.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tibame.group1.db.entity.ChatroomEntity;
import com.tibame.group1.db.repository.ChatroomRepository;
import com.tibame.group1.web.dto.ChatroomIdDTO;
import com.tibame.group1.web.service.ChatroomService;
@Service
public class ChatroomServiceImpl {
    //implements ChatroomService
//    @Autowired
//    private ChatroomRepository roomRepository;
//
//
//    @Override
//    public ChatroomIdDTO addRoom(Integer userA, Integer userB) {
//        ChatroomEntity room = new ChatroomEntity();
//        room.setUserA(userA);
//        room.setUserB(userB);
//        room = roomRepository.save(room);
//        ChatroomIdDTO roomId = new ChatroomIdDTO();
//        roomId.setRoomId(room.getChatroomId());
//
//        return roomId;
//    }
//
//    @Override
//    public ChatroomIdDTO getRoomId(Integer userA, Integer userB) {
//        ChatroomIdDTO roomId = new ChatroomIdDTO();
//        roomId.setRoomId(roomRepository.findByRoom(userA, userB));
//
//        return roomId;
//    }
//
//    @Override
//    public List<ChatroomIdDTO> getAllRooms(Integer memberId) {
//        ArrayList<ChatroomEntity> rooms = roomRepository.findMemberAllRooms(memberId);
//        ArrayList<ChatroomIdDTO> list = null;
//        for(ChatroomEntity ce: rooms) {
//            ChatroomIdDTO room = new ChatroomIdDTO();
//            if(!ce.getUserA().equals(memberId))
//                room.setUserA(ce.getUserA());
//
//            if(!ce.getUserB().equals(memberId))
//                room.setUserA(ce.getUserB());
//
//            room.setRoomId(ce.getChatroomId());
//            list.add(room);
//       }
//
//        return list;
//
//    }
    
}
