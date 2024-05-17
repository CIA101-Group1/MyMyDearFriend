package com.tibame.group1.web.controller;

import ch.qos.logback.core.model.Model;
import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.db.entity.ProductCategoryEntity;
import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.db.entity.ProductImgEntity;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/")
@Slf4j
public class ProductBackendController {

    @Autowired
    private ProductService productService;

    @PostMapping("productCategory/create")
    @CheckLogin
    public @ResponseBody ResDTO<ProductCategoryCreateResDTO> productCategoryCreate(
            @Valid @RequestBody ProductCategoryCreateReqDTO req) throws IOException {
        ResDTO<ProductCategoryCreateResDTO> res = new ResDTO<>();
        res.setData(productService.productCategoryCreate(req));
        return res;
    }
    @GetMapping("productCategory/getAll")
    public @ResponseBody ResDTO<List<ProductCategoryEntity>> productCategoryGetAll() throws IOException {
        ResDTO<List<ProductCategoryEntity>> res = new ResDTO<>();
        res.setData(productService.productCategoryGetAll());
        return res;
    }
    @PostMapping("productCategory/update")
    @CheckLogin
    public @ResponseBody ResDTO<ProductCategoryUpdateResDTO> productCategoryUpdate(
            @Valid @RequestBody ProductCategoryUpdateReqDTO req) throws IOException {
        ResDTO<ProductCategoryUpdateResDTO> res = new ResDTO<>();
        res.setData(productService.productCategoryUpdate(req));
        return res;
    }

    @PostMapping("product/create")
    @CheckLogin
    public @ResponseBody ResDTO<ProductCreateResDTO> productCreate(
            @Valid @RequestBody ProductCreateReqDTO req, @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) throws IOException {
        ResDTO<ProductCreateResDTO> res = new ResDTO<>();
        res.setData(productService.productCreate(req, loginSource));
        return res;
    }

    @GetMapping("product/getAll")
    public @ResponseBody ResDTO<List<ProductEntity>> productGetAll() throws IOException {
        ResDTO<List<ProductEntity>> res = new ResDTO<>();
        res.setData(productService.productGetAll());
        return res;
    }

//    @PostMapping("product/update")
//    @CheckLogin
//    public @ResponseBody ResDTO<ProductUpdateResDTO> productUpdate(
//            @Valid @RequestBody ProductUpdateReqDTO req, @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) throws IOException {
//        ResDTO<ProductUpdateResDTO> res = new ResDTO<>();
//        log.info("productId:{}", req.getProductId());
//        res.setData(productService.productUpdate(req, loginSource));
//        return res;
//    }

//    0517
    @PostMapping("product/update")
    @CheckLogin
    public ResDTO<ProductUpdateResDTO> productUpdate(
            @Valid @RequestBody ProductUpdateReqDTO req, @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) throws IOException {
        ResDTO<ProductUpdateResDTO> res = new ResDTO<>();
        res.setData(productService.productUpdate(req, loginSource));
        return res;
    }

    /* productImg */

    @PostMapping("productImg/create")
    @CheckLogin
    public @ResponseBody ResDTO<ProductImgCreateResDTO> productImgCreate(
            @Valid @RequestBody ProductImgCreateReqDTO req, @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) throws IOException {
        ResDTO<ProductImgCreateResDTO> res = new ResDTO<>();
        res.setData(productService.productImgCreate(req, loginSource));
        return res;
    }

    @GetMapping("productImg/getAll")
    @CheckLogin
    public @ResponseBody ResDTO<List<ProductImgEntity>> productImgGetAll() throws IOException {
        ResDTO<List<ProductImgEntity>> res = new ResDTO<>();
        res.setData(productService.productImgGetAll());
        return res;
    }

    @PostMapping("productImg/update")
    @CheckLogin
    public @ResponseBody ResDTO<ProductImgUpdateResDTO> productImgUpdate(
            @Valid @RequestBody ProductImgUpdateReqDTO req, @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) throws IOException {
        ResDTO<ProductImgUpdateResDTO> res = new ResDTO<>();
        res.setData(productService.productImgUpdate(req, loginSource));
        return res;
    }

    @GetMapping("product/query")
        public ResDTO<List<ProductEntity>> queryGetAll(
                @RequestParam(name = "name", required = false) String name, @RequestParam(name = "description", required = false) String description, @RequestParam(name = "categoryId", required = false) Integer categoryId,
                @RequestParam(name = "reviewStatus", required = false) Integer reviewStatus, @RequestParam(name = "productStatus", required = false) Integer productStatus){
        ResDTO<List<ProductEntity>> res = new ResDTO<>();
        var reqDTO = ProductQueryReqDTO.
                builder().
                name(name).
                description(description).
                categoryId(categoryId).
                reviewStatus(reviewStatus).
                productStatus(productStatus).build();
        res.setData(productService.queryGetAll(reqDTO));
        return res;
    }

}
