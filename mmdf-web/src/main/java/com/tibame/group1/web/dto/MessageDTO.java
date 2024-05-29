package com.tibame.group1.web.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class MessageDTO {
    private Integer roomId;
    private String type;
    private Integer sender;
    private Integer receiver;
    private String Integer;
    private String message;
    private Timestamp date;//XXX
    private byte[] img;
}
