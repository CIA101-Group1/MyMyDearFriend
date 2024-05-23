package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.web.BidProductAddReqDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.BidProductService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BidProductApiController {

    @Autowired BidProductService bidProductService;

    @GetMapping("/bidproduct")
    public ResDTO<List> findAll() {
        ResDTO<List> res = new ResDTO<>();
        res.setData(bidProductService.findAll());
        return res;
    }

    @GetMapping("/bidproduct/{productId}")
    public ResDTO<BidProductEntity> findById(@PathVariable("productId") Integer productId) {
        ResDTO<BidProductEntity> res = new ResDTO<>();
        BidProductEntity bidProductEntity = bidProductService.findById(productId);
        res.setData(bidProductEntity);
        return res;
    }

    @GetMapping("/seller/bidproduct")
    @CheckLogin(isVerified = false)
    public ResDTO<List> findAllForSeller(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<List> res = new ResDTO<>();
        res.setData(bidProductService.findAllForSeller(loginSource));
        return res;
    }

    @PostMapping("/bidproduct")
    @CheckLogin(isVerified = false)
    public @ResponseBody ResDTO<?> add(
            @Valid BidProductAddReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
            throws DateException, IOException, CheckRequestErrorException {

        if (req.getImages()[0].isEmpty()) {
            throw new CheckRequestErrorException("商品圖片：請上傳1到3張圖片");
        }
        for (MultipartFile image : req.getImages()) {
            if (image.getSize() > 5 * 1024 * 1024) {
                throw new CheckRequestErrorException("商品圖片：檔案必須小於5MB");
            }
        }
        bidProductService.add(req, loginSource);
        return new ResDTO<>();
    }

    @PutMapping("/bidproduct/{productId}")
    @CheckLogin(isVerified = false)
    public @ResponseBody ResDTO<?> update(
            @PathVariable(name = "productId") Integer productId,
            @Valid BidProductAddReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
            throws DateException, IOException, CheckRequestErrorException {

        if (req.getImages()[0].isEmpty()) {
            throw new CheckRequestErrorException("商品圖片：請上傳1到3張圖片");
        }
        for (MultipartFile image : req.getImages()) {
            if (image.getSize() > 5 * 1024 * 1024) {
                throw new CheckRequestErrorException("商品圖片：檔案必須小於5MB");
            }
        }
        bidProductService.update(productId, req, loginSource);
        return new ResDTO<>();
    }

    @GetMapping("/bidproduct/query")
    // @CheckLogin(isVerified = false)
    public ResDTO<List<BidProductEntity>> findByCompositeQuery(
            @RequestParam(name = "categoryId", required = false) Integer categoryId,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "status", required = false) List<Integer> status) {
        ResDTO<List<BidProductEntity>> res = new ResDTO<>();
        res.setData(bidProductService.findByCompositeQuery(categoryId, name, status));
        return res;
    }

    // @GetMapping("/bidproduct")
    // public ResDTO<List<BidProductEntity>> findAllByStatus(@RequestParam("status") Integer status) {
    //     ResDTO<List<BidProductEntity>> res = new ResDTO<>();
    //     res.setData(bidProductService.findByStatus(status));
    //     return res;
    // }
}
