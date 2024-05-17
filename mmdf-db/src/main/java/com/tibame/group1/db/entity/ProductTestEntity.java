package com.tibame.group1.db.entity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "general_product")
public class ProductTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "seller_id")
    private Integer sellerId;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "description", nullable = false, length = 200)
    private String description;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "review_status")
    private Integer reviewStatus;

    @Column(name = "product_status")
    private Integer productStatus;

}