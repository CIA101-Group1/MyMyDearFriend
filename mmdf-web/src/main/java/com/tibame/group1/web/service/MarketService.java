package com.tibame.group1.web.service;

import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.web.dto.*;


import java.util.List;

public interface MarketService {
    //獲取後台狀態為已上架的市集
    List<MarketResDTO> getMarketByStatus();

    MarketResDTO marketDetail(Integer marketId) throws CheckRequestErrorException;

    //根據會員id查詢所有報名紀錄
    List<MarketRegistrationResDTO> findAllByMemberId(LoginSourceDTO loginSource);

    //會員報名
    MemberRegistrationResDTO registerMemberToMarket(MarketRegistrationReqDTO marketRegistrationResDTO, LoginSourceDTO loginSource)
            throws CheckRequestErrorException;

    //取消報名
    MarketCancelResDTO cancelRegistration(MarketRegistrationReqDTO marketRegistrationReq, LoginSourceDTO loginSource) throws CheckRequestErrorException;
}

