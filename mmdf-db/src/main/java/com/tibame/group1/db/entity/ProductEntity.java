package com.tibame.group1.db.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "general_product")
public class ProductEntity {
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

        @Column(name = "review_status", nullable = false)
        private Integer reviewStatus;

        @Column(name = "product_status", nullable = false)
        private Integer productStatus;

//        public ProductEntity(){
//        }

//        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "productImgEntity")
//        @OrderBy("productId asc")
//        @JsonIgnore
//        private Set<ProductEntity> getProducts() {
//                return this.products;
//        }
//
//        private void setProducts(Set<ProductEntity> products) {
//                this.products = products;
//        }

}
