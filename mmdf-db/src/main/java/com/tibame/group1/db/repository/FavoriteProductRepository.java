package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.FavoriteProductEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProductEntity, Integer> {

    List<FavoriteProductEntity> findByMemberId(Integer memberId);

    void deleteByMemberIdAndProductId(Integer memberId, Integer productId);
}
