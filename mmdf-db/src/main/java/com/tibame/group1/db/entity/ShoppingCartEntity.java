package com.tibame.group1.db.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

@Getter
@Setter
@Entity
@Table(name = "shopping_cart")
@Immutable
public class ShoppingCartEntity {
    @Id
    @Column(name = "member_id", nullable = false)
    private Integer member;

    @Id
    @Column(name = "product_id", nullable = false)
    private Integer product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
