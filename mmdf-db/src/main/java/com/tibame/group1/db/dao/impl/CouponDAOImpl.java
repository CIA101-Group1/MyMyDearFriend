package com.tibame.group1.db.dao.impl;

import com.tibame.group1.db.dao.CouponDAO;
import com.tibame.group1.db.dto.CouponQueryParams;
import com.tibame.group1.db.dto.CouponReqDTO;
import com.tibame.group1.db.entity.CouponEntity;
import com.tibame.group1.db.rowmapper.CouponRowMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CouponDAOImpl implements CouponDAO {

  @Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public List<CouponEntity> getCoupons(CouponQueryParams couponQueryParams) {
    String sql = "SELECT coupon_id, title, low_price, discount, number, date_start, date_end, addable, livemode " +
            "FROM coupon WHERE 1=1";

    Map<String, Object> map = new HashMap<>();

    if (couponQueryParams.getCouponEffectCategory() != null) {
      sql = sql + " AND livemode = :livemode";
      map.put("livemode", couponQueryParams.getCouponEffectCategory().name());
    }

    if (couponQueryParams.getCouponStackCategory() != null) {
      sql = sql + " AND addable = :addable";
      map.put("addable", couponQueryParams.getCouponStackCategory().name());
    }

    // 加入SQL查詢語句 >> 去對應:search物件
    if (couponQueryParams.getSearch() != null) {
      sql = sql + " AND date_start LIKE :search";
      map.put("search", "%" + couponQueryParams.getSearch() + "%");
    }

    // 加入日期範圍限制
    sql = sql + " AND date_start >= :threeMonthsAgo";
    map.put("threeMonthsAgo", calculateThreeMonthsAgo());

    //排序
    sql = sql + " ORDER BY " + couponQueryParams.getOrderBy() + " " + couponQueryParams.getSort();

    //分頁
    sql = sql + " LIMIT :limit OFFSET :offset";
    map.put("limit", couponQueryParams.getLimit());
    map.put("offset", couponQueryParams.getOffset());

    List<CouponEntity> couponList = namedParameterJdbcTemplate.query(sql, map, new CouponRowMapperUtils());

    return couponList;
  }

  private Date calculateThreeMonthsAgo() {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH, -3);
    return calendar.getTime();
  }

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
    map.put("livemode", couponReqDTO.getAddable().toString());


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
    map.put("livemode", couponReqDTO.getAddable().toString());

    namedParameterJdbcTemplate.update(sql, map);
  }

  @Override
  public void deleteCouponByID(Integer couponID) {
    String sql = "DELETE FROM coupon WHERE coupon_id = :couponID";

    Map<String, Object> map = new HashMap<>();
    map.put("couponID", couponID);

    namedParameterJdbcTemplate.update(sql, map);
  }

  @Override
  public Integer countCoupon(CouponQueryParams couponQueryParams) {
    String sql = "SELECT count(*) FROM coupon WHERE 1=1 ";

    Map<String, Object> map = new HashMap<>();

    if (couponQueryParams.getCouponStackCategory() != null) {
      sql = sql + " AND addable = :addable";
      map.put("addable", couponQueryParams.getCouponStackCategory().name());
    }

    // 加入SQL查詢語句 >> 去對應:serch物件
    if (couponQueryParams.getSearch() != null) {
      sql = sql + " AND date_Start LIKE :search";
      map.put("search", "%" + couponQueryParams.getSearch() + "%");
    }

    Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

    return total;
  }
}
