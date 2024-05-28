package com.tibame.group1.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatroomInit {
    private String type = "sendChatOk";
    private Integer memberId;
}
