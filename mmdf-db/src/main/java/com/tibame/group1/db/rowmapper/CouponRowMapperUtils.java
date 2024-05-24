package com.tibame.group1.db.rowmapper;

import com.tibame.group1.common.enums.CouponEffectCategory;
import com.tibame.group1.common.enums.CouponStackCategory;
import com.tibame.group1.db.entity.CouponEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CouponRowMapperUtils implements RowMapper<CouponEntity> {

  @Override
  public CouponEntity mapRow(ResultSet resultSet, int i) throws SQLException {
    CouponEntity couponEntity = new CouponEntity();

    couponEntity.setCouponID(resultSet.getInt("coupon_ID"));
    couponEntity.setTitle(resultSet.getString("title"));
    couponEntity.setLowPrice(resultSet.getInt("low_price"));
    couponEntity.setDiscount(resultSet.getInt("discount"));
    couponEntity.setNumber(resultSet.getInt("number"));
    couponEntity.setDateStart(resultSet.getTimestamp("date_start"));
    couponEntity.setDateEnd(resultSet.getTimestamp("date_end"));

    couponEntity.setAddable(CouponStackCategory.valueOf(resultSet.getString("addable")));
    couponEntity.setLivemode(CouponEffectCategory.valueOf(resultSet.getString("livemode")));

    return couponEntity;
  }
}
