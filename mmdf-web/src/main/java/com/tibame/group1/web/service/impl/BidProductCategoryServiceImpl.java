package com.tibame.group1.web.service.impl;

import com.tibame.group1.db.entity.BidProductCategoryEntity;
import com.tibame.group1.db.repository.BidProductCategoryRepository;
import com.tibame.group1.web.service.BidProductCategoryService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class BidProductCategoryServiceImpl implements BidProductCategoryService {

    @Autowired private BidProductCategoryRepository bidProductCategoryRepository;

    @Override
    public List<BidProductCategoryEntity> findAll() {
        return bidProductCategoryRepository.findAll();
    }
}
