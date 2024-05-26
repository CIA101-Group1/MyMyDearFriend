package com.tibame.group1.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatroomImgDTO {
    private String type;
    private String sender;
    private String friend;
    private byte[] imgData;
}
