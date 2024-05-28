package com.tibame.group1.db.entity;

import com.tibame.group1.common.enums.WalletCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "wallet_history")
public class WalletHistoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "wallet_history_id", nullable = false)
  private Integer walletHistoryID;

  @Column(name = "change_time", nullable = false)
  private Date changeTime;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "member_id", nullable = false)
  private MemberEntity member;

  @Column(name = "change_amount", nullable = false)
  private Integer changeAmount;

  @Column(name = "change_type", nullable = false)
  private WalletCategory changeType;
}
