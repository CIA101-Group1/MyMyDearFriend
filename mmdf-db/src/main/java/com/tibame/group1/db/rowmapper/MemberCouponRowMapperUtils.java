package com.tibame.group1.db.rowmapper;

import com.tibame.group1.db.entity.MemberCouponEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberCouponRowMapperUtils implements RowMapper<MemberCouponEntity> {

    @Override
    public MemberCouponEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        MemberCouponEntity memberCoupon = new MemberCouponEntity();
        memberCoupon.setSerialCouponID(rs.getInt("serial_coupon_id"));
        memberCoupon.setMemberID(rs.getInt("member_id"));
        memberCoupon.setCouponID(rs.getInt("coupon_id"));
        memberCoupon.setStack(rs.getInt("stack"));
        memberCoupon.setIsUsed(rs.getBoolean("is_used"));
        memberCoupon.setGetTime(rs.getDate("get_time"));
        memberCoupon.setUseTime(rs.getDate("use_time"));
        return memberCoupon;
    }
}
