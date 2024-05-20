package com.tibame.group1.db.dao.impl;

import com.tibame.group1.db.dao.CouponDAO;
import com.tibame.group1.db.dto.CouponReqDTO;
import com.tibame.group1.db.entity.CouponEntity;
import com.tibame.group1.db.rowmapper.CouponRowMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CouponDAOImpl implements CouponDAO {

  @Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public CouponEntity getCouponByID(Integer couponID) {
    String sql =
            "SELECT coupon_id, title, low_price, discount, number, date_start, date_end, addable, livemode " +
                    "FROM coupon WHERE coupon_id = :coupon_id";


    Map<String, Object> map = new HashMap<>();
    map.put("coupon_id", couponID);

    List<CouponEntity> couponEntitiesList =
        namedParameterJdbcTemplate.query(sql, map, new CouponRowMapperUtils());

    if (couponEntitiesList.size() > 0) {
      return couponEntitiesList.get(0);
    } else {
      return null;
    }
  }

  @Override
  public Integer createCoupon(CouponReqDTO couponReqDTO) {

    String sql =
            "INSERT INTO coupon (title, low_price, discount, number, date_start, date_end, addable, livemode) "
                    + "VALUES (:title, :lowPrice, :discount, :number, :dateStart, :dateEnd, :addable, :livemode)";


    Map<String, Object> map = new HashMap<>();
    map.put("title", couponReqDTO.getTitle());
    map.put("lowPrice", couponReqDTO.getLowPrice());
    map.put("discount", couponReqDTO.getDiscount());
    map.put("number", couponReqDTO.getNumber());
    map.put("dateStart", couponReqDTO.getDateStart());
    map.put("dateEnd", couponReqDTO.getDateEnd());
    map.put("addable", couponReqDTO.getAddable().toString());
    map.put("livemode", couponReqDTO.getLivemode());


    KeyHolder keyHolder = new GeneratedKeyHolder();

    namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

    int couponID = keyHolder.getKey().intValue();

    return couponID;
  }

  @Override
  public void updateCoupon(Integer couponID, CouponReqDTO couponReqDTO) {

    String sql =
            "UPDATE coupon SET title = :title, low_price = :lowPrice, discount = :discount, number = :number, date_start = :dateStart, date_end = :dateEnd, addable = :addable, livemode = :livemode" +
                    " WHERE coupon_id = :couponID";

    Map<String, Object> map = new HashMap<>();
    map.put("couponID", couponID);

    map.put("title", couponReqDTO.getTitle());
    map.put("lowPrice", couponReqDTO.getLowPrice());
    map.put("discount", couponReqDTO.getDiscount());
    map.put("number", couponReqDTO.getNumber());
    map.put("dateStart", couponReqDTO.getDateStart());
    map.put("dateEnd", couponReqDTO.getDateEnd());
    map.put("addable", couponReqDTO.getAddable().toString());
    map.put("livemode", couponReqDTO.getLivemode());

    namedParameterJdbcTemplate.update(sql, map);
  }
}
