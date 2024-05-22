package com.tibame.group1.db.entity;

import com.tibame.group1.common.enums.CouponEffectCategory;
import com.tibame.group1.common.enums.CouponStackCategory;
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

       @Column(name = "date_start", nullable = false)
       private Date dateStart;

       @Column(name = "date_end", nullable = false)
       private Date dateEnd;

       @Column(name = "addable", nullable = false)
       private CouponStackCategory addable;

       @Column(name = "livemode", nullable = false)
       private CouponEffectCategory livemode;

}


