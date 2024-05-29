package com.tibame.group1.db.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member_message")
@Getter
@Setter
public class MessageEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="message_id")
	private Integer messageId;
    
    @Column(name="member_chatroom_id")
    private Integer roomId;
    
    //確認訊息是History 還是 New
    @Column(name="message_type")
    private String type;
    
    //訊息本體
    @Column(name="message_content")
    private String message;
    
    //發送者會員編號
    @Column(name="message_sender")
    private Integer sender;
    
    //接收者
    @Column(name="message_receiver")
    private Integer receiver;
    
    @Column(name="message_date")
    private Timestamp date;


    @Column(name="message_img",columnDefinition = "mediumblob")
    private byte[] img;
}
