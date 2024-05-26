package com.tibame.group1.web.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.tibame.group1.db.entity.ChatroomEntity;
import com.tibame.group1.db.entity.ChatroomRedisEntity;
import com.tibame.group1.db.entity.MessageEntity;
import com.tibame.group1.db.repository.MessageRepository;
import com.tibame.group1.web.dto.MessageDTO;
import com.tibame.group1.web.service.MessageService;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private StringRedisTemplate redisTemplate; 
    
    @Autowired
    private MessageRepository messageRepository;
    
    private Gson gson;
    
    @Override
    public void sendMessage(MessageDTO meg, Integer memberId) {
//        ChatroomRedisEntity redis = null;
//        Date date = null;
//        Timestamp time = new Timestamp(date.getTime());
//        ListOperations opsList = redisTemplate.opsForList();
//        redis.setDate(time);
//        redis.setMessage(meg.getMessage());
//        redis.setReceiver(meg.getReceiver());
//        redis.setRoomId(meg.getRoomId());
//        redis.setSender(meg.getSender());
//        redis.setType("new");
//        String messageData = gson.toJson(redis,ChatroomEntity.class);
//        opsList.leftPush(memberId.toString(), messageData);
        
    }


      @Override
      public List<MessageDTO> getMessage(Integer roomId, Integer userId) {

          return null;
      }


      @Override
      public void sendOfflineMessage(MessageDTO meg) {
//          MessageEntity megEnt = null;
//          megEnt.setDate(meg.getDate());
//          megEnt.setMessage(meg.getMessage());
//          megEnt.setReceiver(meg.getReceiver());
//          megEnt.setRoomId(megEnt.getRoomId());
//          megEnt.setSender(meg.getSender());
//          megEnt.setType("History");
//          megEnt = messageRepository.save(megEnt);
      
      }
}