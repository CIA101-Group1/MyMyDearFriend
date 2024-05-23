package com.tibame.group1.web.service.impl;

import com.google.gson.Gson;
import com.tibame.group1.db.repository.AIMessageResponseRepository;
import com.tibame.group1.web.dto.AiMessageDTO;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class webSocketService implements WebSocketHandler {

    private ConcurrentMap<WebSocketSession, String> customers = new ConcurrentHashMap<>();

    private Gson gson = new Gson();

    @Autowired private AIMessageResponseRepository aiRepostiory;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String memberId = session.getUri().toString().split("=")[1];
        customers.put(session, memberId);
        String openSerivce = gson.toJson("{type:Welcome ,message: 歡迎使用客服聊天系統!\n請說明具體的問題}");
        session.sendMessage(new TextMessage(openSerivce));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {
        String custMessage = message.getPayload().toString();
        AiMessageDTO data = gson.fromJson(custMessage, AiMessageDTO.class);
        String getAnswerData = sendAIMessage(data.getAiMessage());
        AiMessageDTO sendMessage = new AiMessageDTO();
        if (getAnswerData == null) {
            sendMessage.setType("none");
            sendMessage.setAiMessage("對不起，我不清楚您的問題。請問是否要幫您聯絡真人客服?");
            sendMessage.setAiMethod("放置路徑使用");
            String send = gson.toJson(sendMessage);
            session.sendMessage(new TextMessage(send));
            return;
        }
        sendMessage.setType("Helper");
        sendMessage.setAiMessage(getAnswerData);
        sendMessage.setAiMethod("path");
        String send = gson.toJson(sendMessage);
        session.sendMessage(new TextMessage(send));
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

    private String sendAIMessage(String message) {
        System.out.println("啟動小幫手");
        AIMessageService aiMessage = new AIMessageService();
        String answer = aiMessage.getAnswer(message);
        if ("none".equals(answer) || answer == null) {
            return null;
        }
        return answer;
    }
}
