package com.tibame.group1.db.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShoppingCartPK implements Serializable {
    private Integer member;
    private Integer product;
}
