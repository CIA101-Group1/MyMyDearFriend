package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "market")
@Setter
@Getter
public class MarketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_id")
    private Integer marketId;

    @Column(name = "market_name",nullable = false,length = 20)
    private String marketName;

    @Column(name = "market_description", nullable = false, length = 1000)
    private String marketDescription;

    @Column(name = "image", columnDefinition = "mediumblob")
    private byte[] marketImage;

    @Column(name = "market_location", nullable = false, length = 20)
    private String marketLocation;

    //活動開始時間
    @Column(name = "market_start")
    private Date marketStart;

    //活動結束時間
    @Column(name = "market_end")
    private Date marketEnd;

    @Column(name = "market_fee", nullable = false , length = 20)
    private Integer marketFee;

    //目前活動報名人數
    @Column(name = "applicant_population")
    private Integer applicantPopulation;

    //活動人數上限
    @Column(name = "applicant_limit",nullable = false,length = 20)
    private Integer applicantLimit;

    //報名開始時間
    @Column(name = "start_date")
    private Date startDate;

    //報名結束時間
    @Column(name = "end_date")
    private Date endDate;

    //活動狀況
    @Column(name = "market_status")
    private Integer marketStatus;

}
