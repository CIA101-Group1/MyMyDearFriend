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
@Table(name = "bid_product")
@Immutable
public class BidProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "seller_id", nullable = false)
    private Integer sellerId;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "start_price", nullable = false)
    private Integer startPrice;

    @Column(name = "duration", nullable = false)
    private Date duration;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "image", columnDefinition = "mediumblob")
    private byte[] image;

    @Column(name = "last_modified")
    private LocalDateTime lastModified;
}
