package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.db.entity.BidProductCategoryEntity;
import com.tibame.group1.web.service.BidProductCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BidProductCategoryApiController {

    @Autowired BidProductCategoryService bidProductCategoryService;

    @GetMapping("/bidproductcategory")
    public ResDTO<List<BidProductCategoryEntity>> findAll() {
        ResDTO<List<BidProductCategoryEntity>> res = new ResDTO<>();
        res.setData(bidProductCategoryService.findAll());
        return res;
    }
}
