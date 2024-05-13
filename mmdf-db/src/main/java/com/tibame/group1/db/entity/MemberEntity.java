package com.tibame.group1.db.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Immutable;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(
        name = "member",
        indexes = {
            @Index(name = "member_account_index", columnList = "member_account", unique = true),
            @Index(name = "email_index", columnList = "email"),
            @Index(name = "is_verified_index", columnList = "is_verified"),
            @Index(name = "seller_status_index", columnList = "seller_status")
        })
@Immutable
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Column(name = "member_account", nullable = false, length = 20)
    private String memberAccount;

    @Column(name = "cid", nullable = false, length = 200)
    private String cid;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "birth", nullable = false)
    private Date birth;

    @Column(name = "tw_person_id", nullable = false, length = 10)
    private String twPersonId;

    @Column(name = "city", nullable = false, length = 20)
    private String city;

    @Column(name = "address", nullable = false, length = 100)
    private String address;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    @Column(name = "verify_sending_time")
    private Date verifySendingTime;

    @Column(name = "verified_time")
    private Date verifiedTime;

    @Column(name = "join_time")
    private Date joinTime;

    @Column(name = "wallet_amount")
    private Integer walletAmount = 0;

    @Column(name = "wallet_available_amount")
    private Integer walletAvailableAmount = 0;

    @Column(name = "wallet_cid", nullable = false, length = 20)
    private String walletCid;

    @Column(name = "wallet_question", nullable = false, length = 100)
    private String walletQuestion;

    @Column(name = "wallet_answer", nullable = false, length = 100)
    private String walletAnswer;

    @Column(name = "seller_status", nullable = false)
    private Boolean sellerStatus = false;

    @Column(name = "score_number")
    private Integer scoreNumber;

    @Column(name = "score_sum")
    private Integer scoreSum;

    @Column(name = "image", columnDefinition = "mediumblob")
    private byte[] image;
}
