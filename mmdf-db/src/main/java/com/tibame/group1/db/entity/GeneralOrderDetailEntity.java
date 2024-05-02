package com.tibame.group1.db.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Immutable;

@Getter
@Setter
@Entity
@Table(name = "general_order_detail")
@Immutable
public class GeneralOrderDetailEntity {
    @Id
    @Column(name = "order_id", nullable = false)
    private Integer order;

    @Id
    @Column(name = "product_id", nullable = false)
    private Integer product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Integer price;
}
