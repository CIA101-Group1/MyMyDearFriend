package com.tibame.group1.admin.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Timestamp;
@Getter
@Setter
public class ServiceMessageDTO {
    private String type;
    private String message_type;
    private String message;
    private Timestamp date;
    private Integer memberId;
    private Integer serviceId;
}
