package com.tibame.group1.admin.service;

import com.tibame.group1.common.dto.web.BidProductAddReqDTO;
import com.tibame.group1.common.enums.BidProductStatus;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.BidProductEntity;

import java.io.IOException;
import java.util.List;

public interface BidProductService {
    List<BidProductEntity> findAll();

    BidProductEntity findById(Integer productId);

    void updateBidProductReviewStatus(Integer productId, Integer newStatus) throws CheckRequestErrorException;

    // void update(Integer productId, BidProductAddReqDTO req, LoginSourceDTO loginSource) throws IOException, CheckRequestErrorException;

    List<BidProductEntity> findByCompositeQuery(Integer categoryId, String name, List<Integer> status);

    // Integer update(EmpUpdateReqDTO req) throws CheckRequestErrorException, IOException;

}
