package com.tibame.group1.db.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailPK implements Serializable {
    private Integer orderId;
    private Integer productId;
}
