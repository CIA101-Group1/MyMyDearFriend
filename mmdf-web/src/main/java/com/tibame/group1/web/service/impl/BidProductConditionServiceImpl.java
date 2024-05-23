package com.tibame.group1.web.service.impl;

import com.tibame.group1.db.entity.BidProductConditionEntity;
import com.tibame.group1.db.repository.BidProductConditionRepository;
import com.tibame.group1.web.service.BidProductConditionService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
public class BidProductConditionServiceImpl implements BidProductConditionService {

    @Autowired private BidProductConditionRepository bidProductConditionRepository;

    @Override
    public List<BidProductConditionEntity> findAll() {
        return bidProductConditionRepository.findAll();
    }
}
