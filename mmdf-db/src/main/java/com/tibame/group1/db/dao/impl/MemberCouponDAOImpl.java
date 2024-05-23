package com.tibame.group1.db.dao.impl;

import com.tibame.group1.db.dao.MemberCouponDAO;
import com.tibame.group1.db.entity.CouponEntity;
import com.tibame.group1.db.entity.MemberCouponEntity;
import com.tibame.group1.db.rowmapper.CouponRowMapperUtils;
import com.tibame.group1.db.rowmapper.MemberCouponRowMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
*TODO
* 會員要看得到自己拿到哪些優惠卷
* 後台完美一點，還要去寫優惠卷自動下架的方式
* */

@Component
public class MemberCouponDAOImpl implements MemberCouponDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public CouponEntity findCouponById(Integer couponID) {
        String sql = "SELECT * FROM coupon WHERE coupon_id = :couponID";
        Map<String, Object> params = new HashMap<>();
        params.put("couponID", couponID);

    List<CouponEntity> coupons =
        namedParameterJdbcTemplate.query(sql, params, new CouponRowMapperUtils());
        return coupons.isEmpty() ? null : coupons.get(0);
    }

    @Override
    public Integer saveMemberCoupon(MemberCouponEntity memberCouponEntity) {
        String sql = "INSERT INTO member_coupon (member_id, coupon_id, stack, is_used, get_time, use_time) " +
                "VALUES (:memberID, :couponID, :stack, :isUsed, :getTime, :useTime)";
        Map<String, Object> params = new HashMap<>();
        params.put("memberID", memberCouponEntity.getMemberID());
        params.put("couponID", memberCouponEntity.getCouponID());
        params.put("stack", memberCouponEntity.getStack());
        params.put("isUsed", memberCouponEntity.getIsUsed());
        params.put("getTime", memberCouponEntity.getGetTime());
        params.put("useTime", memberCouponEntity.getUseTime());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public MemberCouponEntity findLatestMemberCoupon() {
        String sql = "SELECT * FROM member_coupon ORDER BY get_time DESC LIMIT 1";
        Map<String, Object> params = new HashMap<>();

        List<MemberCouponEntity> memberCoupons = namedParameterJdbcTemplate.query(sql, params, new MemberCouponRowMapperUtils());
        return memberCoupons.isEmpty() ? null : memberCoupons.get(0);
    }
}