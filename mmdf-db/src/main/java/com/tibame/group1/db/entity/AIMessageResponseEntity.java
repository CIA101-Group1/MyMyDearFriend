package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "ai_customer_service")
public class AIMessageResponseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageid")
    private Integer messageId;
    @Column(name = "question")
    private String question;
    @Column(name = "answer")
    private String answer;
}
