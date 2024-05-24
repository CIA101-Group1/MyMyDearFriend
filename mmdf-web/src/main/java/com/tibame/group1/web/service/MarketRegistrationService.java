package com.tibame.group1.web.service;

import com.tibame.group1.db.entity.MarketRegistrationEntity;

import java.util.List;
import java.util.Optional;

public interface MarketRegistrationService {
    //獲取所有報名紀錄
    List<MarketRegistrationEntity> getAllRegistration();

    //根據市集id和會員id查詢報名紀錄
    Optional<MarketRegistrationEntity> getRegistration(MarketRegistrationEntity.MarketRegistrationId id);

    //會員報名市集
    MarketRegistrationEntity registerMemberForMarket(Integer marketId, Integer memberId) throws Exception;

    //取消報名
    void cancelRegistration(MarketRegistrationEntity.MarketRegistrationId id) throws Exception;
}
