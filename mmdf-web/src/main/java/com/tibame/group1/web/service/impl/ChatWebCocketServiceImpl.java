package com.tibame.group1.web.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import com.tibame.group1.db.entity.MessageEntity;
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
import com.google.gson.JsonParser;
import com.tibame.group1.db.entity.ChatroomEntity;
import com.tibame.group1.db.entity.ChatroomRedisEntity;
import com.tibame.group1.db.repository.ChatroomRepository;
import com.tibame.group1.db.repository.MessageRepository;
import com.tibame.group1.web.dto.ChatStateDTO;
import com.tibame.group1.web.dto.MemberFriendsDTO;
import com.tibame.group1.web.dto.MessageDTO;

public class ChatWebCocketServiceImpl implements WebSocketHandler {

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

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // =====建立取得會員編號的方法====//
        //        String memberId = session.getUri().toString().split("=")[1];
        //        Integer memberIdInteger = Integer.parseInt(memberId);
        //        if (!sessions.containsKey(memberId)) {
        //            System.out.println(memberId + "State : 初始化...");
        //            initData.redisGetSqlData(memberIdInteger);
        //        }
        //
        //        // ============ 設定Session時效 ============//
        //
        //        if (sessions.get(memberId) == null && session != null) {
        //            List<WebSocketSession> list = new ArrayList();
        //            list.add(session);
        //            sessions.put(memberId, list);
        //        } else if (sessions.get(memberId).contains(session)) {
        //            List<WebSocketSession> list = new ArrayList();
        //            list = sessions.get(memberId);
        //            list.add(session);
        //            sessions.put(memberId, list);
        //        }

        //        sessions.computeIfAbsent(memberId, n -> new
        // ArrayList<WebSocketSession>(session)).add();

        //        List<Integer> userNames = new ArrayList<Integer>();
        //        for (String userName : sessions.keySet()) {
        //            Integer userNameInteger = Integer.parseInt(userName);
        //            System.out.println(userNameInteger);
        //            userNames.add(memberIdInteger);
        //        }
        //        ChatStateDTO stateMessage = new ChatStateDTO("open", memberIdInteger, userNames);
        //        String stateMessageJson = gson.toJson(stateMessage);
        //        Collection<List<WebSocketSession>> sess = sessions.values();
        //        for (List<WebSocketSession> list : sess) {
        //            for (WebSocketSession se : list) {
        //                if (se.isOpen()) {
        //                    se.sendMessage(new TextMessage(stateMessageJson));
        //                }
        //            }
        //        }
        //        System.out.println(sessions);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {

        String payload = message.getPayload().toString();
        JsonObject jsonObj = gson.fromJson(payload, JsonObject.class);
        Integer memberId = jsonObj.get("memberId").getAsInt();
        String type = jsonObj.get("type").getAsString();

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
                    memberSession.put(session, memberId);
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
            Date date = new Date();
            Timestamp time = new Timestamp(date.getTime());
            MessageDTO messageDTO =
                    gson.fromJson(message.getPayload().toString(), MessageDTO.class);
            messageDTO.setSender(sender);
            messageDTO.setDate(time);
            messageDTO.setRoomId(chatroomRepository.findByRoom(receiver, sender));
            messageSaveRedis(messageDTO);

            isMemberOnline(receiver, message, messageDTO);
            isMemberOnline(sender, message, messageDTO);
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
        if (sessions.get(memberId) == null) {
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

    //    private void findMemberFriends(WebSocketSession session, Integer memberId) throws
    // IOException {
    //        List<ChatroomEntity> roomUsers = chatroomRepository.findMemberFriends(memberId);
    //        List<Integer> friends = new ArrayList();
    //        for (ChatroomEntity friend : roomUsers) {
    //            if (friend.getUserA() != null || friend.getUserB() != null) {
    //                if (!friend.getUserA().equals(memberId)) friends.add(friend.getUserA());
    //                if (!friend.getUserB().equals(memberId)) friends.add(friend.getUserB());
    //            }
    //        }
    //        for (Integer member : friends) {
    //            System.out.println(member);
    //        }
    //        Integer number = friends.size();
    //        MemberFriendsDTO responseFriends = new MemberFriendsDTO();
    //        responseFriends.setType("Friends");
    //        responseFriends.setNumber(number);
    //        responseFriends.setFriends(friends);
    //        String responseFriendJson = gson.toJson(responseFriends);
    //        System.out.println(responseFriendJson);
    //        TextMessage send = new TextMessage(responseFriendJson);
    //        System.out.println(send);
    //        session.sendMessage(send);
    //    }

    private void messageSaveRedis(MessageDTO message) {
        //        String messageString = message.getPayload().toString();
        System.out.println("會員編號：" + message.getSender() + " -> 正在儲存傳送訊息的快取 ");
        ChatroomRedisEntity redisMessage = new ChatroomRedisEntity();
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
        String receiverKey =
                "chatroom:" + redisMessage.getReceiver() + ":" + redisMessage.getRoomId();
        String senderKey =
                "chatroom:" + redisMessage.getReceiver() + ":" + redisMessage.getRoomId();
        ops.rightPush(receiverKey, redisMessageJson);
        ops.rightPush(senderKey, redisMessageJson);
        System.out.println("會員編號：" + message.getSender() + " -> 完成儲存傳送訊息的快取!");
    }

    // ============ 解析HashCode驗證碼 (return member) ============//
    private void prseVerification() {}

    //    @Scheduled(fixedRate = 60000)
    //    private void checkMemberTimeOut(){
    //        long currentTime = System.currentTimeMillis();
    //        Set<String> keys = redisTemplate.keys("chatSession:*");
    //        for(String key : keys){
    //            String memberData = redisTemplate.opsForValue().get(key);
    //            String sk = "1001,1707885889092";
    //            long lastTime =  Long.parseLong(sk.split(",")[1]);
    //            if(currentTime - lastTime > 900000){
    //
    //            }
    //
    //        }
    //    }
    private void isMemberOnline(
            Integer memberId, WebSocketMessage<?> message, MessageDTO messageDTO)
            throws IOException {
        ConcurrentLinkedQueue<WebSocketSession> receiverSessionAll =
                sessions.get(memberId.toString());
        if (messageDTO.getSender() == memberId) {
            System.out.println("會員編號：" + memberId + " -> 正在敲敲門~看對方在不在家");
        }
        if (receiverSessionAll != null) {

            for (WebSocketSession receiverSession : receiverSessionAll) {
                if (receiverSession.isOpen()) {
                    receiverSession.sendMessage(message);
                    if (messageDTO.getSender() == memberId) {
                        System.out.println("會員編號：" + memberId + " -> 對方竟然在家裡，真宅呢!");
                    }
                }
            }
        } else {
            if (messageDTO.getSender() == memberId) {
                System.out.println("會員編號：" + memberId + " -> 喔歐~對方不在家，先方在信箱好了");
            }
            // MessageDTO messageDTO =gson.fromJson(message.getPayload().toString(),MessageDTO.class);
            MessageEntity messageEntity = new MessageEntity();
            //messageEntity.setImg(messageDTO.getImg());
            messageEntity.setMessage(messageDTO.getMessage());
            messageEntity.setSender(messageDTO.getSender());
            messageEntity.setReceiver(messageDTO.getReceiver());
            messageEntity.setDate(messageDTO.getDate());
            messageEntity.setType("History");
            messageEntity.setRoomId(messageDTO.getRoomId());
            if (messageDTO.getSender() == memberId) {
                System.out.println("會員編號：" + memberId + " -> 已經把信件放入信箱中");
            }
        }
    }
}
