package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.exception.AuthorizationException;
import com.tibame.group1.db.repository.ChatroomRepository;
import com.tibame.group1.web.service.ChatroomAddFriendService;
import com.tibame.group1.web.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatroomAddFriendServiceImpl implements ChatroomAddFriendService {
    @Autowired private JwtService jwtService ;
    @Autowired private ChatroomRepository chatroomRepository;
    @Override
    public void addFriend(String authorization, Integer memberId) throws AuthorizationException {
        Integer meId = jwtService.decodeLogin(authorization).getMemberId();
        if(meId.equals(memberId)){
            return;
        }
        if(!chatroomRepository.findCheckRoom(meId,memberId)){
            chatroomRepository.addChatroom(meId,memberId);
        }else{
            Integer roomId = chatroomRepository.findByRoom(meId,memberId);
            chatroomRepository.updateNewMessageDate(roomId);
        }
    }
}
