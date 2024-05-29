package com.tibame.group1.web.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.tibame.group1.db.repository.AIMessageResponseRepository;
import com.tibame.group1.web.dto.AiMessageDTO;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WebSocketHelper implements WebSocketHandler {

    private ConcurrentMap<WebSocketSession, Integer> sessionMap = new ConcurrentHashMap<>();
    private JiebaSegmenter segmenter = new JiebaSegmenter();
    private Gson gson = new Gson();

    @Autowired private AIMessageResponseRepository aiMessageResponseRepository;
    @Autowired private JwtService jwtService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        AiMessageDTO dto = new AiMessageDTO();
        dto.setType("welcome");
        dto.setAiMessage("歡迎使用客服聊天系統!\n請簡單說明的問題。");
        String openSerivce = gson.toJson(dto);
//        String openSerivce = gson.toJson("{type:\"welcome\" ,aiMessage: \"歡迎使用客服聊天系統!\n請說明具體的問題\"}");
        session.sendMessage(new TextMessage(openSerivce));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
            throws Exception {
        JsonObject jsonObj = gson.fromJson(message.getPayload().toString(), JsonObject.class);
        String type = jsonObj.get("type").getAsString();
        if (!sessionMap.containsKey(session)) {
            //            Integer memberId = jsonObj.get("memberId").getAsInt();
            //            sessionMap.put(session, memberId);
            // ==================================正式的用法====================================//
            String authorization = jsonObj.get("authorization").getAsString(); //
            LoginSourceDTO loginSource = jwtService.decodeLogin(authorization); //
            sessionMap.put(session, loginSource.getMemberId()); //
            // ==============================================================================//
        }
        if("question".equals(type)){

            String question = jsonObj.get("message").getAsString();
            System.out.println(question);
            if("專員客服".equals(question)){
                Map<String,String> messageType = new HashMap<>();
                messageType.put("type","serviceLive");
                messageType.put("memberId",sessionMap.get(session).toString());
                session.sendMessage(new TextMessage(gson.toJson(messageType)));

                return;
            }
            String answer = getAnswer(question);
            System.out.println(answer);
            AiMessageDTO aiMessageDTO = new AiMessageDTO();
            aiMessageDTO.setAiMessage(answer);
            aiMessageDTO.setType("answer");
            aiMessageDTO.setAiMethod("");

            session.sendMessage(new TextMessage(gson.toJson(aiMessageDTO)));

        }
        if("service".equals(type)){
            String link = "/service/live";
            session.sendMessage(new TextMessage(link));
        }


    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception)
            throws Exception {}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
            throws Exception {
        sessionMap.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    // ============ 到資料庫比較分詞組合尋找正確答案 ============//
    public String getAnswer(String question) {
        Long starTime = System.nanoTime();
        List<String> questionKeys = getQuestionKeys(question);
        for (String questionKey : questionKeys) {
            String key = String.valueOf(questionKey).replaceAll(" ", "");
            //            System.out.println(key);
            //            if (knowledgeBase.containsKey(key)) {
            //                return knowledgeBase.get(key);
            //            }
            Optional<String> keyData = aiMessageResponseRepository.findAnswer(key);
            if (keyData.isPresent()) {
                Long endTime = System.nanoTime();
                System.out.println((endTime - starTime) / 1_000_000 + " 毫秒");
                System.out.println("解答：->" + key);
                String answerData = keyData.get();
                return answerData;
            }
        }
        Long endTime = System.nanoTime();
        System.out.println((endTime - starTime) / 1_000_000 + " 毫秒");
        List<String> sendMessage = new ArrayList<>();
        String m1 = "小麥不知道你在說甚麼，可以簡單形容給小麥聽嗎?";
        String m2 = "小麥聽不懂你在說甚麼，你可以傳送 -> 客服專員 <-，會有比小麥厲害的人跟你回答。";
        String m3 = "小麥放棄了，你可以傳送 -> 客服專員 <- 會有真人客服與你聯繫。";
        String m4 = "這是甚麼東西阿~可以講清楚一點，給小麥知道嗎?";
        sendMessage.add(m1);
        sendMessage.add(m2);
        sendMessage.add(m3);
        sendMessage.add(m4);

        String sendA =sendMessage.get((int)(Math.random()*4));
        return sendA;
    }

    // ============ 分析有哪些分詞 ============//
    private List<String> getQuestionKeys(String question) {
        List<SegToken> tokens = segmenter.process(question, JiebaSegmenter.SegMode.INDEX);
        List<String> words = new ArrayList<>();
        for (SegToken token : tokens) {
            words.add(token.word);
        }

        // ----------生成所有可能的詞組合-----------//
        List<String> combinations = generateCombinations(words);
        return combinations;
    }

    // ============ 重新排列組合分詞 ============//
    private List<String> generateCombinations(List<String> words) {
        List<String> combinations = new ArrayList<>();
        int n = words.size();
        for (int i = 0; i < (1 << n); i++) {
            StringBuilder combination = new StringBuilder();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    combination.append(words.get(j));
                }
            }
            if (combination.length() > 0) {
                combinations.add(combination.toString().trim());
            }
        }
        combinations.sort(Comparator.comparingInt(String::length).reversed());
        return combinations;
    }
}
