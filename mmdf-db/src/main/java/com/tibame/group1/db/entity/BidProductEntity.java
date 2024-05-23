package com.tibame.group1.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tibame.group1.common.enums.BidProductStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "bid_product")
public class BidProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    // @NotNull
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "seller_id", nullable = false)
    // private MemberEntity seller;

    @NotNull
    @Column(name = "seller_id", nullable = false)
    private Integer sellerId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false, insertable = false, updatable = false)
    private com.tibame.group1.db.entity.BidProductCategoryEntity category;

    @NotNull
    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "condition_id", nullable = false, insertable = false, updatable = false)
    private com.tibame.group1.db.entity.BidProductConditionEntity condition;

    @NotNull
    @Column(name = "condition_id", nullable = false)
    private Integer conditionId;

    @Size(max = 40)
    @NotNull
    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Size(max = 400)
    @NotNull
    @Column(name = "description", nullable = false, length = 400)
    private String description;

    @NotNull
    @Column(name = "start_price", nullable = false)
    private Integer startPrice;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8")
    @Column(name = "start_time")
    private Timestamp startTime;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8")
    @Column(name = "end_time")
    private Timestamp endTime;

    @NotNull
    @Column(name = "status", nullable = false)
    private Integer status;

    // @Enumerated(EnumType.ORDINAL)
    // @Column(name = "status", nullable = false)
    // private BidProductStatus status;

    // public String getStatus() {
    //     return BidProductStatus.fromValue(status).getDescription();
    // }
    public String getStatus() {
        return BidProductStatus.fromValue(status).getDescription();
    }

    public void setStatus(BidProductStatus status) {
        this.status = status.getValue();
    }

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8")
    @Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;

    // @OneToMany(mappedBy = "product")
    // private Set<com.tibame.group1.db.entity.BidEntity> bids = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product")
    @OrderBy("imageId asc")
    private Set<com.tibame.group1.db.entity.BidProductImageEntity> images =
            new LinkedHashSet<>();
}
