package com.tibame.group1.db.rowmapper;

import com.tibame.group1.db.entity.CouponEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CouponRowMapperUtils implements RowMapper<CouponEntity> {

  @Override
  public CouponEntity mapRow(ResultSet resultSet, int i) throws SQLException {
    CouponEntity couponEntity = new CouponEntity();

    couponEntity.setCouponID(resultSet.getInt("COUPON_ID"));
    couponEntity.setTitle(resultSet.getString("TITLE"));
    couponEntity.setLowPrice(resultSet.getInt("LOW_PRICE"));
    couponEntity.setDiscount(resultSet.getInt("DISCOUNT"));
    couponEntity.setNumber(resultSet.getInt("NUMBER"));
    couponEntity.setDateStart(resultSet.getTimestamp("DATE_START"));
    couponEntity.setDateEnd(resultSet.getTimestamp("DATE_END"));
    couponEntity.setAddable(resultSet.getString("ADDABLE"));
    couponEntity.setLivemode(resultSet.getString("LIVEMONE"));

    return couponEntity;
  }
}
