package com.tibame.group1.db.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class FavoriteProductListPK implements Serializable {
    private Integer member;
    private Integer product;
}
