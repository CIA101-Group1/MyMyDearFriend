package com.tibame.group1.web.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.tibame.group1.db.entity.ChatroomEntity;
import com.tibame.group1.db.entity.ChatroomRedisEntity;
import com.tibame.group1.db.entity.MessageEntity;
import com.tibame.group1.db.repository.ChatroomRepository;
import com.tibame.group1.db.repository.MessageRepository;
import com.tibame.group1.web.service.RedisAndSQLConnectSevice;

@Service
public class RedisAndSQLConnectSeviceImpl implements RedisAndSQLConnectSevice {

    @Autowired private MessageRepository messageRepository;

    @Autowired private ChatroomRepository chatroomRepository;

    @Autowired private StringRedisTemplate redisTemplate;

    private Gson gson = new Gson();

    @Override
    public void redisGetSqlData(Integer memberId) throws NullPointerException {
        ArrayList<ChatroomEntity> rooms = chatroomRepository.findMemberAllRooms(memberId);
        // =============== 檢查有沒有朋友 ===============//
        if (rooms == null || rooms.size() == 0) {
            System.out.println("會員編號：" + memberId + " -> 因沒有朋友，所以不想理你");
            return;
        }
        System.out.println("會員編號：" + memberId + " -> 正在進行MySQL資料送至Redis");
        Integer count = 0;
        // ============ 取得聊天室ID ============//
        for (ChatroomEntity roomId : rooms) {
            ChatroomRedisEntity redis = new ChatroomRedisEntity();

            // ============ 取得各個聊天室的資料 ============//
            ArrayList<MessageEntity> messages = null;
            messages = messageRepository.findByRoomId(roomId.getChatroomId());

            // ============ 檢查是否為空值或有無資料 ============//
            if (messages != null && !redisTemplate.hasKey(String.valueOf(memberId))) {

                // =========== 將SQL的資訊寫入Redis中 ============//
                ListOperations opsList = redisTemplate.opsForList();
                for (MessageEntity messageEnt : messages) {
                    // ------阻擋存入已重複的訊息------//
                    redis.setMessage(messageEnt.getMessage());
                    redis.setReceiver(messageEnt.getReceiver());
                    redis.setSender(messageEnt.getSender());
                    redis.setRoomId(messageEnt.getRoomId());
                    redis.setType(messageEnt.getType());
                    redis.setDate(messageEnt.getDate());
                    redis.setImg(messageEnt.getImg());
                    String redisData = gson.toJson(redis, ChatroomRedisEntity.class);
                    String redisKey = "chatroom" + ":" + memberId + ":" + messageEnt.getRoomId();
                    opsList.rightPush(redisKey, redisData);
                    count++;
                }
            }
        }
        System.out.println("會員編號：" + memberId + " -> 已完成寫入，共有 " + count + " 則訊息已寫入Redis");
    }

    // 可能要非同步 synchronized  ??
    @Override
    public void redisSetSqlData(String memberId) {

        // ============ 取得Redis的資料 ===========//
        ListOperations list = redisTemplate.opsForList();
        Set<String> keys = redisTemplate.keys("chatroom" + ":" + memberId + ":*");
        for (String key : keys) {
            List<String> redisData = list.range(key, 0, -1);

            // ============ 寫入MySQL ============//
            System.out.println("會員編號：" + memberId + " -> 正在儲存訊息至MySQL");
            int count = 0;
            for (String redis : redisData) {
                ChatroomRedisEntity data = gson.fromJson(redis, ChatroomRedisEntity.class);

                // ------ 檢查資料是否已存在MySQL ------//
                if (!"History".equals(data.getType())) {
                    if (messageRepository.FindIsData(data.getRoomId(), data.getDate())) continue;
                    MessageEntity sendSqlData = new MessageEntity();
                    sendSqlData.setDate(data.getDate());
                    sendSqlData.setMessage(data.getMessage());
                    sendSqlData.setReceiver(data.getReceiver());
                    sendSqlData.setRoomId(data.getRoomId());
                    sendSqlData.setSender(data.getSender());
                    sendSqlData.setType("History");
                    sendSqlData.setImg(data.getImg());
                    sendSqlData = messageRepository.save(sendSqlData);
                    count++;
                }
            }
            System.out.println("會員編號：" + memberId + " -> 已儲存 " + count + " 則訊息至MySQL");
        }
    }

    @Override
    public void deleteRedisData(String memberId) {
        System.out.println("會員編號：" + memberId + " -> 正在刪除快取中...");
        Set<String> key = redisTemplate.keys("chatroom" + ":" + memberId + ":*");
        Long deleteRedisData = redisTemplate.delete(key);
        if (deleteRedisData > 0) {
            System.out.println("會員編號：" + memberId + " -> 刪除快取完成!");
        } else {
            System.out.println("會員編號：" + memberId + " -> 刪除快取失敗!");
        }
    }
}
