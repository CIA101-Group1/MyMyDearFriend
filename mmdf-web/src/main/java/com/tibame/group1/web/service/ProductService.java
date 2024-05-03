package com.tibame.group1.web.service;

import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.ProductCreateReqDTO;
import com.tibame.group1.web.dto.ProductCreateResDTO;

import java.io.IOException;

public interface ProductService {

    @CheckLogin
    ProductCreateResDTO productCreate(ProductCreateReqDTO req) throws IOException;
}
