package com.tibame.group1.web.service.impl;

import com.tibame.group1.db.entity.AIMessageResponseEntity;
import com.tibame.group1.db.repository.AIMessageResponseRepository;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class AIMessageService {

    private HashMap<String, String> questionData;

    @Autowired private AIMessageResponseRepository aiRepostiory;

    public AIMessageService() {
        // ============ init ============//
        //        questionData = new HashMap<>();
        //        List<AIMessageResponseEntity> aiData = aiRepostiory.findAll();
        //        for (AIMessageResponseEntity aiEnt : aiData){
        //            questionData.put(aiEnt.getQuestion(), aiEnt.getAnswer());
        //        }
    }

    public String getAnswer(String question) {
        Document doc = new Document(question);
        for (Sentence sent : doc.sentences()) {
            String questionKey = getQuestionKey(sent.text());
            if (questionData.containsKey(questionKey)) {
                return questionData.get(questionKey);
            }
        }
        System.out.println("正在找尋解決問題的方法...");
        return "查無方法";
    }

    private String getQuestionKey(String question) {
        Document doc = new Document(question);
        StringBuilder keyBuilder = new StringBuilder();
        System.out.println("正在分析問題....");
        for (Sentence sent : doc.sentences()) {
            List<String> words = sent.words();
            //            List<String> posTags = sent.posTags()
            List<String> postags = sent.posTags();
            for (String posTag : postags) {
                for (String word : words) {
                    if (posTag.startsWith("N") || posTag.startsWith("V")) {
//                        String word = sent.word(i);
                        keyBuilder.append(word.toLowerCase());
                    }
                }
            }
        }

        return keyBuilder.toString().trim();
    }
}
