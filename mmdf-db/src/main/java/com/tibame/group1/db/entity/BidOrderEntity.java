package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "bid_order")
public class BidOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private com.tibame.group1.db.entity.BidProductEntity product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "buyer_id", nullable = false)
    private MemberEntity buyer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    private MemberEntity seller;

    @Column(name = "member_coupon_id1")
    private Integer memberCouponId1;

    @Column(name = "member_coupon_id2")
    private Integer memberCouponId2;

    @NotNull
    @Column(name = "price_before_discount", nullable = false)
    private Integer priceBeforeDiscount;

    @NotNull
    @Column(name = "discount", nullable = false)
    private Integer discount;

    @NotNull
    @Column(name = "price_after_discount", nullable = false)
    private Integer priceAfterDiscount;

    @NotNull
    @Column(name = "create_time", nullable = false)
    private Timestamp createTime;

    @NotNull
    @Column(name = "order_status", nullable = false)
    private Integer orderStatus;

    @Size(max = 20)
    @NotNull
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Size(max = 20)
    @NotNull
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Size(max = 40)
    @NotNull
    @Column(name = "address", nullable = false, length = 40)
    private String address;

    @NotNull
    @Column(name = "fee", nullable = false)
    private Integer fee;
}
