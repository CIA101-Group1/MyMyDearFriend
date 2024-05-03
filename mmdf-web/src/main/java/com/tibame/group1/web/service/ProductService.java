package com.tibame.group1.web.service;

import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.web.dto.*;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductCreateResDTO productCreate(ProductCreateReqDTO req) throws IOException;
    List<ProductEntity> productGetAll() throws IOException;
    ProductUpdateResDTO productUpdate(ProductUpdateReqDTO req) throws IOException;
}
