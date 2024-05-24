package com.tibame.group1.web.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WebSocketLiveService implements WebSocketHandler {

    private ConcurrentMap<WebSocketSession, LoginSourceDTO> members = new ConcurrentHashMap<>();
    private ConcurrentMap<WebSocketSession,String> serivces = new ConcurrentHashMap<>();
    private List<WebSocketSession> waitMembers = Collections.synchronizedList(new ArrayList<>());
    private Gson gson = new Gson();
    @Autowired private JwtService jwtService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {}

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {
        JsonObject jsonObj = gson.fromJson(message.getPayload().toString(), JsonObject.class);
        String type = jsonObj.get("type").getAsString();
        if ("member".equals(type)) {
            String authorization = jsonObj.get("authorization").getAsString();
            LoginSourceDTO loginSource = jwtService.decodeLogin(authorization);
            members.put(session, loginSource);
            waitMembers.add(session);
        }
        if("service".equals(type)){

        }

        if("service_getCustomer".equals(type)){
            if(serivces.containsKey(session)&&serivces.get(session) == jsonObj.get("id").getAsString()){

            }
        }
        if("message".equals(type)){

        }
        if("end".equals(type)){

        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
            throws Exception {}

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
