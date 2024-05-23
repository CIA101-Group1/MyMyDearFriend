package com.tibame.group1.db.entity;

import jakarta.persistence.*;

import lombok.Data;

import org.springframework.data.annotation.Immutable;

import java.io.Serializable;

@Data
@Entity
@IdClass(FavoriteProductEntity.FavoriteProductListPK.class)
@Table(name = "favorite_product_list")
@Immutable
public class FavoriteProductEntity {
    @Id
    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Id
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Data
    public static class FavoriteProductListPK implements Serializable {
        private Integer memberId;
        private Integer productId;
    }
}
