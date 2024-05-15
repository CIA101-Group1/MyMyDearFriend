package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "wallet_history",
        indexes = {
                @Index(columnList = "wallet_history", unique = true),
        }
)
public class WalletHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_history_id", nullable = false)
    private Integer walletHistoryID;

    @Column(name = "change_time", nullable = false)
    private Timestamp changeTime;

    @Column(name = "member_id", nullable = false)
    private Integer memberID;

    @Column(name = "change_amount", nullable = false)
    private Integer changeAmount;

    @Column(name = "change_type", nullable = false)
    private Integer changeType;
    
}

