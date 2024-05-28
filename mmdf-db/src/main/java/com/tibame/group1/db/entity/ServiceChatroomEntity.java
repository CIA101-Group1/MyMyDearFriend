package com.tibame.group1.db.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="service_chatroom")
@Getter
@Setter
public class ServiceChatroomEntity {
	@Id
	@Column(name="messageId")
	private Integer messageId;//存入SQL用
	
	@Column(name="customerId")
	private Integer customerId;
	
	@Column(name="serviceId")
	private Integer serviceId;
	
	@Column(name="message")
	private String message;
	
	@Column(name="message_type")
	private String type;
	
	@Column(name="message_date")
	private Timestamp date;
}
