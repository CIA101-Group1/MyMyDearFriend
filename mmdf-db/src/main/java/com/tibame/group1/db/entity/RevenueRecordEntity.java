package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
@Entity
@Getter
@Setter
@Table(name="revenue_record")
public class RevenueRecordEntity {
    @Id
    @Column(name = "trade_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tradeId;
    @Column(name = "date")
    private Timestamp date;
    @Column(name = "trade_amount")
    private Integer tradeAmount;
    @Column(name="charge_fee")
    private Integer chargeFee;
    @Column(name="order_type")
    private String type;
    @Column(name="order_id")
    private Integer id;
}
