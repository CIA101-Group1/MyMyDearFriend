package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.utils.ConvertUtils;
import com.tibame.group1.common.utils.DateUtils;
import com.tibame.group1.db.entity.MarketEntity;
import com.tibame.group1.db.repository.MarketRepository;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.dto.MarketResDTO;
import com.tibame.group1.web.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class MarketServiceImpl implements MarketService {

    @Autowired
    private MarketRepository marketRepository;

    @Override
    public List<MarketResDTO> getMarketByStatus(LoginSourceDTO loginSource) {
        List<MarketEntity> markets = marketRepository.findByMarketStatus(2);

        // 將查詢結果轉換為MarketResDTO
        List<MarketResDTO> marketList = new ArrayList<>();
        for (MarketEntity market : markets) {
            MarketResDTO resDTO = new MarketResDTO();
            resDTO.setMarketId(String.valueOf(market.getMarketId()));
            resDTO.setMarketName(market.getMarketName());
            resDTO.setMarketDescription(market.getMarketDescription());
            resDTO.setMarketLocation(market.getMarketLocation());
            resDTO.setMarketStart(DateUtils.dateToSting(market.getMarketStart()));
            resDTO.setMarketEnd(DateUtils.dateToSting(market.getMarketEnd()));
            resDTO.setMarketFee(market.getMarketFee());
            resDTO.setApplicantPopulation(market.getApplicantPopulation());
            resDTO.setApplicantLimit(market.getApplicantLimit());
            resDTO.setStartDate(DateUtils.dateToSting(market.getStartDate()));
            resDTO.setEndDate(DateUtils.dateToSting(market.getEndDate()));
            resDTO.setMarketStatus(market.getMarketStatus());
            resDTO.setMarketImage(
                    null == market.getMarketImage()
                            ? null
                            : "data:image/png;base64, "
                                    + ConvertUtils.bytesToBase64(market.getMarketImage()));
            marketList.add(resDTO);
        }
        return marketList;
        }
}
