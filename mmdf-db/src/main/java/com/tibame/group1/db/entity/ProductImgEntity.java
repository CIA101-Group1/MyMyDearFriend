package com.tibame.group1.db.entity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Immutable;

@Getter
@Setter
@Entity
@Table(name = "general_product_image")
public class ProductImgEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", updatable = false)
    private Integer imageId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "image")
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_product", referencedColumnName = "productId")
    private ProductEntity productEntity;


}
