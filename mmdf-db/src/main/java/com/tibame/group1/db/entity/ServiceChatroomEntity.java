package com.tibame.group1.db.entity;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name="service_chatroom")
@Getter
@Setter
public class ServiceChatroomEntity {
	@Id
	@Column(name="message_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer messageId;//存入SQL用
	
	@Column(name="customer_Id")
	private Integer customerId;
	
	@Column(name="service_Id")
	private Integer serviceId;
	
	@Column(name="message")
	private String message;
	
	@Column(name="message_type")
	private String type;
	
	@Column(name="message_date")
	private Timestamp date;

}
