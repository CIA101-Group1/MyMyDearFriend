package com.tibame.group1.web.service;

import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.dto.MarketResDTO;

import java.util.List;

public interface MarketService {
    //獲取後台狀態為已上架的市集
    List<MarketResDTO> getMarketByStatus(LoginSourceDTO loginSource);

}
