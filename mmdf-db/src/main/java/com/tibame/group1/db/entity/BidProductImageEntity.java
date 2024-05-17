package com.tibame.group1.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bid_product_image")
public class BidProductImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false)
    private Integer imageId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    private com.tibame.group1.db.entity.BidProductEntity product;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @NotNull
    @Column(name = "image", nullable = false, columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    @NotNull
    @Column(name = "position", nullable = false)
    private Integer position;
}
