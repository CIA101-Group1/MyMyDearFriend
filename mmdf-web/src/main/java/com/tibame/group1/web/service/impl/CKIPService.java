package com.tibame.group1.web.service.impl;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.tibame.group1.db.repository.AIMessageResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class CKIPService {

    //    private static Map<String, String> knowledgeBase = new HashMap<>();


    @Autowired private AIMessageResponseRepository aiMessageResponseRepository;
    private JiebaSegmenter segmenter = new JiebaSegmenter();

    //    public void CustomerServiceSystem() {
    //        // 初始化知識庫
    //        knowledgeBase = new HashMap<>();
    //        knowledgeBase.put("商品詳情", "您可以進入商品頁面查看商品詳情。");
    //        knowledgeBase.put("忘記密碼", "您可以點擊忘記密碼頁面進行密碼重置。");
    //        // 添加更多問題和答案
    //
    //        segmenter = new JiebaSegmenter();
    //    }
    //    public static void main(String[] args) {
    //        // 初始化知識庫
    //        knowledgeBase.put("商品詳情", "您可以進入商品頁面查看商品詳情。");
    //        knowledgeBase.put("忘記密碼", "您可以點擊忘記密碼頁面進行密碼重置。");
    //        // 添加更多問題和答案
    //
    //
    //    }

    // 根據用戶問題查找答案
    public String getAnswer(String question) {
        Long starTime = System.nanoTime();
        List<String> questionKeys = getQuestionKeys(question);
        for (String questionKey : questionKeys) {
            String key = String.valueOf(questionKey).replaceAll(" ", "");
            System.out.println(key);
            //            if (knowledgeBase.containsKey(key)) {
            //                return knowledgeBase.get(key);
            //            }
            Optional<String> keyData = aiMessageResponseRepository.findAnswer("商品詳情");
            if (keyData.isPresent()) {
                Long endTime = System.nanoTime();
                System.out.println((endTime - starTime) / 1_000_000 + " 毫秒");
                System.out.println("解答：->" + key);
                return keyData.toString();
            }
        }
        Long endTime = System.nanoTime();
        System.out.println((endTime - starTime) / 1_000_000 + " 毫秒");
        return "對不起，我不明白您的問題。請聯繫客服人員獲取幫助。";
    }

    // 使用 jieba 分析問題，提取所有可能的關鍵詞組合作為問題鍵
    private List<String> getQuestionKeys(String question) {
        List<SegToken> tokens = segmenter.process(question, JiebaSegmenter.SegMode.INDEX);
        List<String> words = new ArrayList<>();
        for (SegToken token : tokens) {
            words.add(token.word);
        }

        // 生成所有可能的詞組合
        List<String> combinations = generateCombinations(words);
        return combinations;
    }

    // 生成詞的所有可能組合
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
        // 按長度排序，長的組合優先
        combinations.sort(Comparator.comparingInt(String::length).reversed());
        return combinations;
    }
}
