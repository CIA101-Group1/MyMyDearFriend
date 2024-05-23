package com.tibame.group1.web.service;

import com.tibame.group1.common.dto.web.BidAddReqDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.BidEntity;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.web.dto.LoginSourceDTO;

import java.util.List;

public interface BidService {
    void addBid(BidAddReqDTO bidAddReqDTO, LoginSourceDTO loginSource) throws CheckRequestErrorException;

    List<BidEntity> findBidsByProductId(Integer productId);

    Integer findCurrentPriceForProduct(Integer productId);
}
