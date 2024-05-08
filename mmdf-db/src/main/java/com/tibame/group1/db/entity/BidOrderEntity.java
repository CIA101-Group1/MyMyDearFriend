package com.tibame.group1.db.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Immutable;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "bid_order")
@Immutable
public class BidOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_product_id", nullable = false)
    private Integer bidProductId;

    @Column(name = "seller_id", nullable = false, length = 20)
    private String sellerId;

    @Column(name = "category_id", nullable = false, length = 200)
    private String categoryId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "description", nullable = false, length = 20)
    private String description;

    @Column(name = "start_price", nullable = false, length = 50)
    private String startPrice;

    @Column(name = "duration", nullable = false)
    private Date duration;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "image", columnDefinition = "mediumblob")
    private byte[] image;
}
