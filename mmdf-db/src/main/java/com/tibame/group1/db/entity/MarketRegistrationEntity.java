package com.tibame.group1.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "market_registration")
public class MarketRegistrationEntity {
    //使用複合主鍵
    @EmbeddedId
    private MarketRegistrationId Id;

    @ManyToOne
    //指定marketId是複合主鍵的一部份，將其映射到market實體類的id
    @MapsId("marketId")
    //指定外鍵
    @JoinColumn(name = "market_id")
    private MarketEntity market;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Column(name = "status")
    private Integer status;

    @Column(name = "participate_date")
    private Date participateDate;

    //複合主鍵需實現Serializable
    @Getter
    @Setter
    @Embeddable
    public static class MarketRegistrationId implements Serializable{
        private Integer marketId;
        private Integer memberId;

        //覆寫equals方法
        @Override
        public boolean equals(Object o){
            if ( this == o ) return true; //如果當前對象與參數對象是同一個對象，返回TRUE
            if (o == null || getClass() != o.getClass()) return false; //如果參數對象為NULL或當前對象的類不同，則返回FALSE
            MarketRegistrationId that = (MarketRegistrationId) o; //將參數對象強制轉換為MarketRegistrationId
            return Objects.equals(marketId, that.getMarketId()) &&
                    Objects.equals(memberId, that.getMemberId());
        }

        //覆寫hashCode方法
        @Override
        public int hashCode(){
            return Objects.hash(marketId,memberId);
        }


    }

}


