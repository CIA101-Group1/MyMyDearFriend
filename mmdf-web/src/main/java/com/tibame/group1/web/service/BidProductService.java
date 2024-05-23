package com.tibame.group1.web.service;

import com.tibame.group1.common.dto.web.BidProductAddReqDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.web.dto.LoginSourceDTO;

import java.io.IOException;
import java.util.List;

public interface BidProductService {
    List<BidProductEntity> findAll();

    BidProductEntity findById(Integer productId);

    void add(BidProductAddReqDTO req, LoginSourceDTO loginSource) throws IOException;

    void update(Integer productId, BidProductAddReqDTO req, LoginSourceDTO loginSource) throws IOException, CheckRequestErrorException;

    List<BidProductEntity> findAllForSeller(LoginSourceDTO loginSource);

    List<BidProductEntity> findByCompositeQuery(Integer categoryId, String name, List<Integer> status);

    List<BidProductEntity> findByStatus(Integer status);

    void updateExpiredBidProducts();

}
