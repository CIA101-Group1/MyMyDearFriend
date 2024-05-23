package com.tibame.group1.db.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RedisHash(value="ChatroomMessage")
public class ChatroomRedisEntity implements Serializable {
    private Integer roomId;
    
    private String type;
    
    private String message;
    
    private Integer sender;
    
    private Integer receiver;
    
    private Timestamp date;

    private byte[] img;
    
}
