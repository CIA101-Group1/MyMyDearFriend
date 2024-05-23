package com.tibame.group1.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tibame.group1.common.enums.BidOrderStatus;
import com.tibame.group1.common.enums.BidProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "bid_order")
public class BidOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Integer orderId;

    // @NotNull
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "product_id", nullable = false)
    // private com.tibame.group1.db.entity.BidProductEntity product;
    //
    // @NotNull
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "buyer_id", nullable = false)
    // private MemberEntity buyer;
    //
    // @NotNull
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "seller_id", nullable = false)
    // private MemberEntity seller;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @NotNull
    @Column(name = "buyer_id", nullable = false)
    private Integer buyerId;

    @NotNull
    @Column(name = "seller_id", nullable = false)
    private Integer sellerId;

    @NotNull
    @Column(name = "subtotal", nullable = false)
    private Integer subtotal;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "total")
    private Integer total;

    @NotNull
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8")
    @Column(name = "create_time", nullable = false)
    private Timestamp createTime;

    @NotNull
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8")
    @Column(name = "update_time", nullable = false)
    private Timestamp updateTime;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    public String getStatus() {
        return BidOrderStatus.fromCode(status).getMessage();
    }

    public void setStatus(BidOrderStatus status) {
        this.status = status.getCode();
    }

    public Integer getStatusCode() {
        return this.status;
    }

    @Size(max = 20)
    @Column(name = "name", length = 20)
    private String name;

    @Size(max = 20)
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 40)
    @Column(name = "address", length = 40)
    private String address;

    @Column(name = "fee")
    private Integer fee;
}
