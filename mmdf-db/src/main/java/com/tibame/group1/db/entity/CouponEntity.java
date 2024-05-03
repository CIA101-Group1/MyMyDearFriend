package com.tibame.group1.db.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "coupon",
       indexes = {
        @Index(name = "coupon_index", columnList = "coupon_id", unique = true),
       }
)



public class CouponEntity {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       @Column(name = "coupon_id", nullable = false)
       private Integer couponID;

       @Column(name = "title", nullable = false, length = 50)
       private String title;

       @Column(name = "low_price", nullable = false)
       private Integer lowPrice;

       @Column(name = "discount", nullable = false)
       private Integer discount;

       @Column(name = "number", nullable = false)
       private Integer number;

       @Column(name = "timeStart", nullable = false)
       private Date time_start;

       @Column(name = "timeEnd", nullable = false)
       private Date time_end;

       @Column(name = "addable", nullable = false)
       private Integer addable;

}


