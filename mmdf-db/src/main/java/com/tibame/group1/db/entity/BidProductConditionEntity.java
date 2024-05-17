package com.tibame.group1.db.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bid_product_condition")
public class BidProductConditionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "condition_id", nullable = false)
    private Integer conditionId;

    @Column(name = "condition_name", nullable = false, length = 20)
    private String conditionName;
}
