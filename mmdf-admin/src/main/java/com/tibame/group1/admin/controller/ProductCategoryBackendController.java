package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.dto.ProductCategoryCreateReqDTO;
import com.tibame.group1.admin.dto.ProductCategoryCreateResDTO;
import com.tibame.group1.admin.dto.ProductCategoryUpdateReqDTO;
import com.tibame.group1.admin.dto.ProductCategoryUpdateResDTO;
import com.tibame.group1.admin.service.ProductCategoryService;
import com.tibame.group1.common.dto.ResDTO;
//import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.admin.annotation.CheckLogin;
import com.tibame.group1.db.entity.ProductCategoryEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("mmdf/web/")
public class ProductCategoryBackendController {  //CheckLogin 待確認

    @Autowired
    private ProductCategoryService productCategoryService;

    @PostMapping("productCategory/create")
    @CheckLogin
    public @ResponseBody ResDTO<ProductCategoryCreateResDTO> productCategoryCreate(
            @Valid @RequestBody ProductCategoryCreateReqDTO req) throws IOException {
        ResDTO<ProductCategoryCreateResDTO> res = new ResDTO<>();
        res.setData(productCategoryService.productCategoryCreate(req));
        return res;
    }
    @GetMapping("productCategory/getAll")
    public @ResponseBody ResDTO<List<ProductCategoryEntity>> productCategoryGetAll() throws IOException {
        ResDTO<List<ProductCategoryEntity>> res = new ResDTO<>();
        res.setData(productCategoryService.productCategoryGetAll());
        return res;
    }
    @PostMapping("productCategory/update")
    @CheckLogin
    public @ResponseBody ResDTO<ProductCategoryUpdateResDTO> productCategoryUpdate(
            @Valid @RequestBody ProductCategoryUpdateReqDTO req) throws IOException {
        ResDTO<ProductCategoryUpdateResDTO> res = new ResDTO<>();
        res.setData(productCategoryService.productCategoryUpdate(req));
        return res;
    }
//    @GetMapping("product/getOne")
//    public @ResponseBody ResDTO<ProductGetOneResDTO> productGetOne(
//            @Valid @RequestBody ProductGetOneReqDTO req) throws CheckRequestErrorException {
//        ResDTO<ProductGetOneResDTO> res = new ResDTO<>();
//        res.setData(productService.productGetOne(req));
//        return res;
//    }

}
