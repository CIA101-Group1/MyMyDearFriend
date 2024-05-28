package com.tibame.group1.db.entity;

import org.springframework.data.annotation.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "member_chatroom")
@Getter
@Setter
@Immutable
public class ChatroomEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_chatroom_id")
	private Integer chatroomId;
	
	@Column(name = "member_a_id")
	private Integer userA;
	
	@Column(name="member_b_id")
	private Integer userB;

	@Column(name="date")
	private Timestamp date;
}
