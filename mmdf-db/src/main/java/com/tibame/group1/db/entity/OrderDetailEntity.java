package com.tibame.group1.db.entity;

import jakarta.persistence.*;

import lombok.Data;

import org.springframework.data.annotation.Immutable;

import java.io.Serializable;

@Data
@Entity
@IdClass(OrderDetailEntity.OrderDetailPK.class)
@Table(name = "general_order_detail")
@Immutable
public class OrderDetailEntity {
    @Id
    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    @Id
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Data
    public static class OrderDetailPK implements Serializable {
        private Integer orderId;
        private Integer productId;
    }
}
