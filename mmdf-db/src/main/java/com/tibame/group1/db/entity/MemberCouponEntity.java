package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "member_coupon",
        indexes = {
        @Index(columnList = "member_coupon", unique = true),
        @Index(columnList = "coupon_id", unique = true),
        @Index(columnList = "member_id", unique = true)
})
public class MemberCouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serial_coupon_id", nullable = false)
    private Integer serialCouponID;

}
