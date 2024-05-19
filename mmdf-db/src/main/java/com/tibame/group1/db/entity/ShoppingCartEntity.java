package com.tibame.group1.db.entity;

import jakarta.persistence.*;

import lombok.Data;

import org.springframework.data.annotation.Immutable;

import java.io.Serializable;

@Data
@Entity
@IdClass(ShoppingCartEntity.ShoppingCartPK.class)
@Table(name = "shopping_cart")
@Immutable
public class ShoppingCartEntity {
    @Id
    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Id
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Data
    public static class ShoppingCartPK implements Serializable {
        private Integer memberId;
        private Integer productId;
    }
}
