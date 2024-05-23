package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

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

    @Column(name = "member_id", nullable = false)
    private Integer memberID;

    @Column(name = "coupon_id", nullable = false)
    private Integer couponID;

    @Column(name = "stack", nullable = false)
    private Integer stack;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed;

    @Column(name = "get_time", nullable = false)
    private Date getTime;

    @Column(name = "use_time", nullable = false)
    private Date useTime;

}
