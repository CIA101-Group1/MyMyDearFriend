package com.tibame.group1.web.service;

import java.util.List;

import com.tibame.group1.web.dto.MessageDTO;

public interface MessageService {
    public void sendMessage(MessageDTO meg, Integer memberId);
    
    public List<MessageDTO> getMessage(Integer roomId,Integer userId);
 
    public void sendOfflineMessage(MessageDTO meg);
}

