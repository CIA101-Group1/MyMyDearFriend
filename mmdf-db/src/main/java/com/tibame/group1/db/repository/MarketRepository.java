package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.MarketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarketRepository extends JpaRepository<MarketEntity, Integer> {
    List<MarketEntity> findByMarketStatus(Integer marketStatus);
}
