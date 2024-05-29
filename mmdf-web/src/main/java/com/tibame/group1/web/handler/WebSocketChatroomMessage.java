package com.tibame.group1.web.handler;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import com.tibame.group1.db.entity.MessageEntity;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.JwtService;
import com.tibame.group1.web.service.impl.RedisAndSQLConnectSeviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tibame.group1.db.entity.ChatroomRedisEntity;
import com.tibame.group1.db.repository.ChatroomRepository;
import com.tibame.group1.db.repository.MessageRepository;
import com.tibame.group1.web.dto.MessageDTO;

public class WebSocketChatroomMessage implements WebSocketHandler {

    private Gson gson = new Gson();
    // 使用者代入HashCode ->
    // redis -> memberId : WebSocket : HashCode : Time *****
    // => chatSession : WebSocket : HashCode -> { memberId , lastTime }
    // 上線用戶 使用者資訊更改成 memberId List<WebSocketSession>
    private ConcurrentMap<Integer, ConcurrentLinkedQueue<WebSocketSession>> sessions =
            new ConcurrentHashMap<>();
    //                      WebSocketSession HashSession
    private ConcurrentMap<WebSocketSession, Integer> memberSession = new ConcurrentHashMap<>();

    @Autowired private MessageRepository messageRepository;
    @Autowired private RedisAndSQLConnectSeviceImpl initData;
    @Autowired private StringRedisTemplate redisTemplate;
    @Autowired private ChatroomRepository chatroomRepository;
    @Autowired private JwtService jwtService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {}

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {
        String payload = message.getPayload().toString();
        JsonObject jsonObj = gson.fromJson(payload, JsonObject.class);
        String type = jsonObj.get("type").getAsString();

        if (!memberSession.containsKey(session)) {
            //            System.out.println("進入檢查會員");
            // ===================================================================//
            // System.out.println(jsonObj.get("memberId").getAsInt());            //
            // LoginSourceDTO loginSourceDTO = new LoginSourceDTO();              //
            // loginSourceDTO.setMemberId(jsonObj.get("memberId").getAsInt());    //
            // loginSourceDTO.setName(jsonObj.get("name").getAsString());        //
            // sessionMap.put(session, loginSourceDTO);                          //
            // ==================================正式的用法========================//
            String authorization = jsonObj.get("authorization").getAsString(); //
            Integer memberId = jwtService.decodeLogin(authorization).getMemberId(); //
            memberSession.put(session, memberId); //
            // ==================================================================//
        }

        Integer memberId = memberSession.get(session);

        // ============= 將會員資料送至Map ============//
        if ("init".equals(type)) {
            System.out.println("會員編號：" + memberId + " -> 訊息功能初始化...");
            if (sessions.get(memberId.toString()) == null) {
                System.out.println("會員編號：" + memberId + " -> 尚未在其他設備登入，正在建立資料...");
                ConcurrentLinkedQueue<WebSocketSession> webSocketSessionList =
                        new ConcurrentLinkedQueue<>();
                webSocketSessionList.add(session);
                sessions.put(memberId, webSocketSessionList);
                memberSession.put(session, memberId);
                System.out.println("會員編號：" + memberId + " -> 建立完成");
            } else {
                System.out.println("會員編號：" + memberId + " -> 有在其他設備登入，正在建立資料...");
                if (!sessions.get(memberId).contains(session)) {
                    sessions.get(memberId).add(session);
                }
            }
            System.out.println("會員編號：" + memberId + " -> 訊息功能初始化完成...");

            return;
        }

        // ============ 取的好友歷史聊天紀錄 ============//
        if (payload.contains("getHistory")) {
            Integer friend = jsonObj.get("friendId").getAsInt();

            System.out.println("會員編號：" + memberId + " -> 正在讀取與 " + friend + " 的聊天紀錄");

            Integer roomId = chatroomRepository.findByRoom(memberId, friend);
            String key = "chatroom:" + memberId + ":" + roomId;
            List<String> historyData = redisTemplate.opsForList().range(key, 0, -1);
            String historyMeg = gson.toJson(historyData);
            MessageDTO responseMessage = new MessageDTO();
            responseMessage.setSender(memberId);
            responseMessage.setType("getHistory");
            //            responseMessage.setMemberId(memberId);
            responseMessage.setMessage(historyMeg);
            session.sendMessage(new TextMessage(gson.toJson(responseMessage)));
            System.out.println("會員編號：" + memberId + " -> 正在讀取完畢與 " + friend + " 的聊天紀錄");
            return;
        }

        // ============ 發送訊息 ============//
        if (payload.contains("chat")) {
            Integer receiver = jsonObj.get("receiver").getAsInt();
            System.out.println("會員編號：" + memberId + " -> 正在傳送訊息給 " + receiver);
            //            Integer sender = jsonObj.get("memberId").getAsInt();
            Integer sender = memberId;
            //            Integer roomId = chatroomRepository.findByRoom(receiver, sender);

            // ---------------------------------------//
            Integer roomId = chatroomRepository.findByRoom(receiver, sender);
            Date date = new Date();
            Timestamp time = new Timestamp(date.getTime());
            chatroomRepository.updateNewMessageDate(roomId);
            MessageDTO messageDTO = new MessageDTO();
            if (jsonObj.get("img") != null) {
                byte[] imageBytes =
                        Base64.getDecoder().decode(jsonObj.get("img").getAsString().split(",")[1]);
                messageDTO.setImg(imageBytes);
            }
            messageDTO.setSender(sender);
            messageDTO.setDate(time);
            messageDTO.setRoomId(roomId);
            messageDTO.setType("newMessage");
            messageDTO.setMessage(jsonObj.get("message").getAsString());
            messageSaveRedis(messageDTO, sender);

            //            isMemberOnline(sender, message, messageDTO);
            isMemberOnline(receiver, message, messageDTO);

            //            List<WebSocketSession> receiverSessionAll =
            // sessions.get(receiver.toString());
            //            for (WebSocketSession receiverSession : receiverSessionAll) {
            //                if (receiverSession.isOpen()) {
            //                    receiverSession.sendMessage(message);
            //                }
            //            }
            //            List<WebSocketSession> senderSessionAll =
            // sessions.get(receiver.toString());
            //            for (WebSocketSession receiverSession : receiverSessionAll) {
            //                if (receiverSession.isOpen()) {
            //                    receiverSession.sendMessage(message);
            //                }
            //            }
            return;
        }

        // ============ 發送含圖片訊息 ============//
        //        if (payload.contains("Image")) {
        ////            MessageDTO queestMessage = gson.fromJson(payload, MessageDTO.class);
        //            JsonObject questionMessage = gson.fromJson(payload,JsonObject.class);
        //            String imgJson = questionMessage.get("img").toString();
        //            byte[] imgData = Base64.getDecoder().decode(imgJson);
        //            session.sendMessage(message);
        //            Date date = new Date();
        //            Timestamp time = new Timestamp(date.getTime());
        //            ChatroomRedisEntity redisEntity = new ChatroomRedisEntity();
        //            if("ImageAndMessage".equals(questionMessage.get("type").toString())){
        //                redisEntity.setType("imageAndMessage");
        //                redisEntity.setMessage(questionMessage.get("message").toString());
        //            } else {
        //                redisEntity.setType("image");
        //            }
        //            redisEntity.setDate(time);
        //            redisEntity.setSender(memberId);
        //            redisEntity.setReceiver(questionMessage.get("receiver").getAsInt());
        //            redisEntity.setImg(imgData);
        //            Integer roomId =
        // chatroomRepository.findByRoom(questionMessage.get("Receiver").getAsInt(), memberId);
        //            String key = "chatroom:" + memberId + ":" + roomId;
        //            ListOperations listOps = redisTemplate.opsForList();
        //            listOps.leftPush(key, redisEntity);
        //
        //
        //            List<WebSocketSession> senderSessionAll =
        // sessions.get(questionMessage.get("Receiver").toString());
        //            for (WebSocketSession senderSession : senderSessionAll) {
        //                if (senderSession.isOpen()) {
        //                    senderSession.sendMessage(message);
        //                }
        //            }
        //
        //            List<WebSocketSession> receiverSessionAll = sessions.get(memberId.toString());
        //            for (WebSocketSession receiverSession : receiverSessionAll) {
        //                if (receiverSession.isOpen()) {
        //                    receiverSession.sendMessage(message);
        //                }
        //            }
        //        }
        if (payload.contains("Order")) {}

        System.out.println("WebSocket Error -> 無回應方法");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {
        System.out.println(exception.toString());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
            throws Exception {
        Integer memberId = memberSession.get(session);
        System.out.println("會員編號：" + memberId + " -> 正在離開...");
        sessions.get(memberId).remove(session);
        if (sessions.get(memberId).isEmpty()) {
            System.out.println("會員編號：" + memberId + " -> 會員也沒有在其他地方了，真的離開了!");
            sessions.remove(memberId);
        }
        memberSession.remove(session);
        System.out.println("會員編號：" + memberId + " -> 珍重再見!");
    }

    @Override
    public boolean supportsPartialMessages() {

        return false;
    }

    private void messageSaveRedis(MessageDTO message, Integer memberId) {
        //        String messageString = message.getPayload().toString();
        ChatroomRedisEntity redisMessage = new ChatroomRedisEntity();
        System.out.println("會員編號：" + message.getSender() + " -> 正在儲存傳送訊息的快取 ");
        redisMessage.setImg(message.getImg());
        redisMessage.setMessage(message.getMessage());
        redisMessage.setType("newMessage");
        redisMessage.setDate(message.getDate());
        redisMessage.setSender(message.getSender());
        redisMessage.setRoomId(message.getRoomId());
        redisMessage.setReceiver(message.getReceiver());
        //        redisMessage.setRoomId(roomId);
        //        redisMessage.setDate(time);
        //        redisMessage.setSender(sender);
        String redisMessageJson = gson.toJson(redisMessage);
        ListOperations ops = redisTemplate.opsForList();
        String memberIdKey = "chatroom:" + memberId + ":" + redisMessage.getRoomId();
        ops.rightPush(memberIdKey, redisMessageJson);
        System.out.println("會員編號：" + message.getSender() + " -> 完成儲存傳送訊息的快取!");
    }

    private void isMemberOnline(
            Integer memberId, WebSocketMessage<?> message, MessageDTO messageDTO)
            throws IOException {
        ConcurrentLinkedQueue<WebSocketSession> receiverSessionAll = sessions.get(memberId);
        if (messageDTO.getSender().equals(memberId)) {
            System.out.println("會員編號：" + memberId + " -> 正在傳送訊息");
        }
        if (receiverSessionAll != null) {
            Integer count = 0;
            for (WebSocketSession receiverSession : receiverSessionAll) {
                if (receiverSession.isOpen()) {
                    receiverSession.sendMessage(new TextMessage(gson.toJson(messageDTO)));
                    count++;
                    if (messageDTO.getSender() != memberId) {
                        System.out.println("會員編號：" + memberId + " -> 正在接收訊息");
                    }
                }
            }
            if (count > 0) {
                messageSaveRedis(messageDTO, memberId);
            }
            return;
        } else {
            if (messageDTO.getSender() == memberId) {
                System.out.println("會員編號：" + memberId + " -> 喔歐~對方不在家，先方在信箱好了");
            }
            // MessageDTO messageDTO
            // =gson.fromJson(message.getPayload().toString(),MessageDTO.class);
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setImg(messageDTO.getImg());
            messageEntity.setMessage(messageDTO.getMessage());
            messageEntity.setSender(messageDTO.getSender());
            messageEntity.setReceiver(messageDTO.getReceiver());
            messageEntity.setDate(messageDTO.getDate());
            messageEntity.setType("History");
            messageEntity.setRoomId(messageDTO.getRoomId());
            messageEntity = messageRepository.save(messageEntity);
            if (messageDTO.getSender() == memberId) {
                System.out.println("會員編號：" + memberId + " -> 已經把信件放入信箱中");
            }
        }
    }
}
