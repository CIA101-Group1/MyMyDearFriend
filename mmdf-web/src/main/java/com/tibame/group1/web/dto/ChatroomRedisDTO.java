package com.tibame.group1.web.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatroomRedisDTO {
    private Integer roomId;
    
    private String type;
    
    private String message;
    
    private String sender;
    
    private String receiver;
    
    private Date date; 
}
