package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "wallet_request")
public class WalletRequestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "wallet_request_id", nullable = false)
  private Integer walletRequestId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "member_id", nullable = false)
  private MemberEntity memberId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "wallet_history_id", nullable = false)
  private WalletHistoryEntity walletHistoryID;

  @Column(name = "status", nullable = false)
  private String status;

  @Column(name = "account", nullable = false)
  private String account;

  @Column(name = "request_date", nullable = false)
  private Date requestDate;

  @Column(name = "done_date")
  private Date doneDate;
}
