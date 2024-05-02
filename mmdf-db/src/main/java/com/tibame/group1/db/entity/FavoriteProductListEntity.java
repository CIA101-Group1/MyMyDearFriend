package com.tibame.group1.db.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Immutable;

@Getter
@Setter
@Entity
@Table(name = "favorite_product_list")
@Immutable
public class FavoriteProductListEntity {
    @Id
    @Column(name = "member_id", nullable = false)
    private Integer member;

    @Id
    @Column(name = "product_id", nullable = false)
    private Integer product;
}
