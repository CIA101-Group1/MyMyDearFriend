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
@Table(name = "bid_product_image")
@Immutable
public class BidProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Integer imageId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "image_data", columnDefinition = "mediumblob")
    private byte[] image;

    @Column(name = "order", nullable = false)
    private Integer order;
}
