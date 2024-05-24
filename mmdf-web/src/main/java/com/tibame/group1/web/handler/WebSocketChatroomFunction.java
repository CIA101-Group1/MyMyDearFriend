package com.tibame.group1.web.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tibame.group1.db.entity.ChatroomEntity;
import com.tibame.group1.db.repository.ChatroomRepository;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.web.dto.FriendInfoDTO;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.dto.MemberFriendsDTO;
import com.tibame.group1.web.dto.MessageDTO;
import com.tibame.group1.web.service.JwtService;
import com.tibame.group1.web.service.RedisAndSQLConnectSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.socket.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketChatroomFunction implements WebSocketHandler {

    // ------ 存放會員資料
    private ConcurrentHashMap<WebSocketSession, LoginSourceDTO> sessionMap =
            new ConcurrentHashMap<>();
    // ------ 查看以上線的會員
    private ConcurrentHashMap<Integer, Set<WebSocketSession>> onlineMembers =
            new ConcurrentHashMap<>();

    @Autowired private RedisAndSQLConnectSevice initAndDestroyData;

    @Autowired private JwtService jwtService;

    @Autowired private StringRedisTemplate redisTemplate;

    @Autowired private ChatroomRepository chatroomRepository;

    @Autowired private MemberRepository memberRepository;

    private Gson gson = new Gson();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //        String authorization = jsonObj.get("authorization").getAsString();
        //        LoginSourceDTO loginSource = jwtService.decodeLogin(authorization);

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {
        JsonObject jsonObj = gson.fromJson(message.getPayload().toString(), JsonObject.class);
        String type = jsonObj.get("type").getAsString();

        // ============ 檢查是否有會員資訊 ============//
//        System.out.println("判斷檢查會員");
        if (!sessionMap.containsKey(session)) {
//            System.out.println("進入檢查會員");
            System.out.println(jsonObj.get("memberId").getAsInt());
            LoginSourceDTO loginSourceDTO = new LoginSourceDTO();
            loginSourceDTO.setMemberId(jsonObj.get("memberId").getAsInt());
            loginSourceDTO.setName(jsonObj.get("name").getAsString());
            sessionMap.put(session, loginSourceDTO);
            // ==================================正式的用法====================================//
            //            String authorization = jsonObj.get("authorization").getAsString(); //
            //            LoginSourceDTO loginSource = jwtService.decodeLogin(authorization);//
            //            sessionMap.put(session, loginSource);                              //
            // ==============================================================================//
        }

        Integer memberId = sessionMap.get(session).getMemberId();
        String username = sessionMap.get(session).getName();

        // ============ 表示該會員上線 ============//
        if (onlineMembers.get(memberId) == null || onlineMembers.get(memberId).contains(session)) {
            if (onlineMembers.containsKey(memberId)) {
                onlineMembers.get(memberId).add(session);
            } else {
                onlineMembers.put(memberId, new HashSet<WebSocketSession>());
                onlineMembers.get(memberId).add(session);
            }
        }

        // ============ 初始化聊天室 ============//
        if ("init".equals(type)) {
            System.out.println("會員編號：" + memberId + " -> 正在聊天室初始化...");


            // ------------ 檢查Reids是否有重複資料 ------------//
            Set<String> keys = redisTemplate.keys("chatroom" + ":" + memberId + ":*");
            if (keys == null || keys.size() == 0) {
                System.out.println("會員編號：" + memberId + " -> 正在聊天室讀寫快取...");
                initAndDestroyData.redisGetSqlData(memberId);
            }

            System.out.println("會員編號：" + memberId + " -> 聊天室初始化完成!");
            return;
        }

        // ============ 新增聊天對象 ============//
        if ("addFriend".equals(type)) {

            Integer friend = jsonObj.get("friend").getAsInt();
            System.out.println("會員編號：" + memberId + " -> 正在加入 " + friend + " 進入聊天室~");
            Integer roomId = chatroomRepository.findByRoom(memberId, friend);
            if (roomId < 0) {
                ChatroomEntity chatroomEntity = new ChatroomEntity();
                chatroomEntity.setUserA(memberId);
                chatroomEntity.setUserB(friend);
                chatroomRepository.save(chatroomEntity);
                roomId = chatroomRepository.findByRoom(memberId, friend);
            }
        }

        // ============ 取得好友資訊 ============//
        if ("getFriends".equals(type)) {
            List<ChatroomEntity> roomEnt = chatroomRepository.findMemberFriends(memberId);
            List<Integer> friendsId = new ArrayList<>();
            for (ChatroomEntity friend : roomEnt) {
                if (friend.getUserA() == memberId) {
                    friendsId.add(friend.getUserB());
                } else {
                    friendsId.add(friend.getUserB());
                }
            }
            List<FriendInfoDTO> friendsInfo = new ArrayList<>();
            ListOperations<String,String> listOps = redisTemplate.opsForList();
            for (Integer friendId : friendsId) {
                FriendInfoDTO dto = new FriendInfoDTO();
                dto.setId(friendId);
                dto.setName(memberRepository.findById(friendId).get().getName());
                byte[] avatarBytes = memberRepository.findById(friendId).get().getImage();
                if (avatarBytes != null) {
                    String avatarBase64 = Base64.getEncoder().encodeToString(avatarBytes);
                    dto.setAvatar("data:image/*;base64," + avatarBase64);
                }
                String key = "chatroom:"+memberId+":"+chatroomRepository.findByRoom(memberId,friendId);
                long lastIndex = listOps.size(key)-1;
//                System.out.println(lastIndex);
                String latestMessageRedis = listOps.index(key,lastIndex);
//                System.out.println(latestMessageRedis);
                MessageDTO latestMessageDTO = gson.fromJson(latestMessageRedis,MessageDTO.class);
                if (latestMessageDTO != null) {
                    String latestMessage =
                            latestMessageDTO.getMessage();
                    dto.setLatestMessage(latestMessage);
                }
                friendsInfo.add(dto);
            }
            MemberFriendsDTO memberFriendsDTO = new MemberFriendsDTO();
            memberFriendsDTO.setType("getFriends");
            memberFriendsDTO.setFriends(friendsInfo);
            session.sendMessage(new TextMessage(gson.toJson(memberFriendsDTO)));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
            throws Exception {
        LoginSourceDTO source = sessionMap.get(session);
        System.out.println("會員編號：" + source.getMemberId() + " -> 會員已下線");
        onlineMembers.get(source.getMemberId()).remove(session);
        System.out.println("會員編號：" + source.getMemberId() + " -> 正在判斷該會員是否有在其他裝置登入中...");
        if (onlineMembers.get(source.getMemberId()).size() == 0) {
            System.out.println("會員編號：" + source.getMemberId() + " -> 已判斷無其他裝置，正在進行儲存作業和刪除快取...");
            onlineMembers.remove(source.getMemberId());
            initAndDestroyData.redisSetSqlData(source.getMemberId().toString());
            initAndDestroyData.deleteRedisData(source.getMemberId().toString());
            System.out.println("會員編號：" + source.getMemberId() + " -> 下線處理完畢OK~");
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
