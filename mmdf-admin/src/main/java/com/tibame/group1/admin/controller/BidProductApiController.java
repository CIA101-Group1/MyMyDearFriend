package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.service.BidProductService;
import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.db.entity.BidProductEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BidProductApiController {

    @Autowired BidProductService bidProductService;

    @GetMapping("/bidproduct")
    public ResDTO<List<BidProductEntity>> findAll() {
        ResDTO<List<BidProductEntity>> res = new ResDTO<>();
        res.setData(bidProductService.findAll());
        return res;
    }

    @GetMapping("/bidproduct/{productId}")
    public ResDTO<BidProductEntity> findById(@PathVariable(name = "productId") Integer productId) {
        ResDTO<BidProductEntity> res = new ResDTO<>();
        BidProductEntity bidProductEntity = bidProductService.findById(productId);
        res.setData(bidProductEntity);
        return res;
    }

    @PutMapping("/bidproduct/{productId}")
    public @ResponseBody ResDTO<?> updateBidProductReviewStatus(
            @PathVariable("productId") Integer productId,
            @RequestParam("status") Integer newReviewStatus)
            throws DateException, IOException, CheckRequestErrorException {

        bidProductService.updateBidProductReviewStatus(productId, newReviewStatus);
        return new ResDTO<>();
    }

    @GetMapping("/bidproduct/query")
    public ResDTO<List<BidProductEntity>> findByCompositeQuery(
            @RequestParam(name = "categoryId", required = false) Integer categoryId,
            @RequestParam(name = "conditionId", required = false) Integer conditionId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "status", required = false) List<Integer> status) {
        ResDTO<List<BidProductEntity>> res = new ResDTO<>();
        res.setData(bidProductService.findByCompositeQuery(categoryId, conditionId, name, status));
        return res;
    }
}
