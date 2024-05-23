package com.tibame.group1.web.service;

import com.tibame.group1.db.entity.BidProductCategoryEntity;

import java.util.List;

public interface BidProductCategoryService {
    List<BidProductCategoryEntity> findAll();
}
