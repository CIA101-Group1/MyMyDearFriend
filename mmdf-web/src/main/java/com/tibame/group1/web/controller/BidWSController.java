package com.tibame.group1.web.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
public class BidWSController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/bid")
    @SendTo("/topic/bidUpdate")
    public BidMessage send(BidMessage bidMessage) {
        // Here you would handle the bid logic, saving it to the database, etc.
        return bidMessage;
    }

    public void broadcastBidUpdate(BidMessage bidMessage) {
        this.template.convertAndSend("/topic/bidUpdate", bidMessage);
    }
}

@Setter
@Getter
class BidMessage {
    private Integer productId;
    private double amount;

    // getters and setters
}

