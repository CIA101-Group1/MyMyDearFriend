package com.tibame.group1.db.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Immutable;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "bid")
@Immutable
public class BidEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id", nullable = false)
    private String bidId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "member_id", nullable = false)
    private String memberId;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "bid_time", nullable = false)
    private LocalDateTime bidTime;
}
