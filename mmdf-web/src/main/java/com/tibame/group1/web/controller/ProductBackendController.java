package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("mmdf/web/api/")
public class ProductBackendController {

    @Autowired
    private ProductService productService;

    @PostMapping("product/create")
    @CheckLogin
    public @ResponseBody ResDTO<ProductCreateResDTO> productCreate(
            @Valid @RequestBody ProductCreateReqDTO req) throws IOException {
        ResDTO<ProductCreateResDTO> res = new ResDTO<>();
        res.setData(productService.productCreate(req));
        return res;
    }
    @GetMapping("product/getAll")
    public @ResponseBody ResDTO<List<ProductEntity>> productGetAll() throws IOException {
        ResDTO<List<ProductEntity>> res = new ResDTO<>();
        res.setData(productService.productGetAll());
        return res;
    }
    @PostMapping("product/update")
    @CheckLogin
    public @ResponseBody ResDTO<ProductUpdateResDTO> productUpdate(
            @Valid @RequestBody ProductUpdateReqDTO req) throws IOException {
        ResDTO<ProductUpdateResDTO> res = new ResDTO<>();
        res.setData(productService.productUpdate(req));
        return res;
    }

}
