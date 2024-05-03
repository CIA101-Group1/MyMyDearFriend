package com.tibame.group1.db.entity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Immutable;

@Getter
@Setter
@Entity
@Table(name = "category")
public class ProductCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", updatable = false)
    private Integer categoryId;

    @Column(name = "category_name", nullable = false, length = 20)
    private String categoryName;

}
