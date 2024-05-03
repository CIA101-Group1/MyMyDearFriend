package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.web.dto.ProductCreateReqDTO;
import com.tibame.group1.web.dto.ProductCreateResDTO;
import com.tibame.group1.web.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("mmdf/web/api/")
public class ProductBackendController {

    @Autowired private ProductService productService;

    @PostMapping("product/create")
    public @ResponseBody ResDTO<ProductCreateResDTO> productCreate(
            @Valid @RequestBody ProductCreateReqDTO req) throws IOException {
        ResDTO<ProductCreateResDTO> res = new ResDTO<>();
        res.setData(productService.productCreate(req));
        return res;
    }

}
