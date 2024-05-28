package com.tibame.group1.admin.service;

import com.tibame.group1.common.dto.web.NewsAddReqDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.db.entity.NewsEntity;

import java.io.IOException;
import java.util.List;

public interface NewsService {
    List<NewsEntity> findAll();

    NewsEntity findById(Integer newsId);

    void add(NewsAddReqDTO req) throws IOException;

    void update(Integer productId, NewsAddReqDTO req) throws CheckRequestErrorException, IOException;

    void updateStatus(Integer newsId, Integer newStatus) throws CheckRequestErrorException;

    // List<BidProductEntity> findByCompositeQuery();
}
