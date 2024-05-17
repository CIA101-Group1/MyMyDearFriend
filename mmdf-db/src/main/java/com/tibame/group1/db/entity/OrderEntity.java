package com.tibame.group1.db.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Immutable;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "general_order")
@Immutable
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Integer Id;

    @Column(name = "buyer_id")
    private Integer buyerId;

    @Column(name = "seller_id")
    private Integer sellerId;

    @Column(name = "member_coupon_id1")
    private Integer memberCouponId1;

    @Column(name = "member_coupon_id2")
    private Integer memberCouponId2;

    @Column(name = "price_before_discount", nullable = false)
    private Integer priceBeforeDiscount;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "price_after_discount", nullable = false)
    private Integer priceAfterDiscount;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "order_status", nullable = false)
    private Byte orderStatus;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "address", nullable = false, length = 40)
    private String address;

    @Column(name = "fee", nullable = false)
    private Integer fee;
}
