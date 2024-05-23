package com.tibame.group1.db.specification;

import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.MemberNoticeEntity;

import jakarta.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class MemberNoticeSpecifications {
    public static Specification<MemberNoticeEntity> hasType(String type) {
        return (root, query, criteriaBuilder) -> {
            if (null == type) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("noticeCategory"), type);
        };
    }

    public static Specification<MemberNoticeEntity> timestampBetween(Date start, Date end) {
        return (root, query, criteriaBuilder) -> {
            if (null == start && null == end) {
                return criteriaBuilder.conjunction();
            } else if (null == start) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("sendingTime"), end);
            } else if (null == end) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("sendingTime"), start);
            } else {
                return criteriaBuilder.between(root.get("sendingTime"), start, end);
            }
        };
    }

    public static Specification<MemberNoticeEntity> hasMemberId(Integer memberId) {
        return (root, query, criteriaBuilder) -> {
            if (null == memberId) {
                return criteriaBuilder.conjunction();
            }
            Join<MemberNoticeEntity, MemberEntity> memberJoin = root.join("member");
            return criteriaBuilder.equal(memberJoin.get("memberId"), memberId);
        };
    }
}
