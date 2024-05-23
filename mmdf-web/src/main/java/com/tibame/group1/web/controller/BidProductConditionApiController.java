package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.db.entity.BidProductConditionEntity;
import com.tibame.group1.web.service.BidProductConditionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BidProductConditionApiController {

    @Autowired BidProductConditionService bidProductConditionService;

    @GetMapping("/bidproductcondition")
    public ResDTO<List<BidProductConditionEntity>> findAll() {
        ResDTO<List<BidProductConditionEntity>> res = new ResDTO<>();
        res.setData(bidProductConditionService.findAll());
        return res;
    }
}
