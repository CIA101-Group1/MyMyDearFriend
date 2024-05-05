package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.ProductCategoryCreateReqDTO;
import com.tibame.group1.admin.dto.ProductCategoryCreateResDTO;
import com.tibame.group1.admin.dto.ProductCategoryUpdateReqDTO;
import com.tibame.group1.admin.dto.ProductCategoryUpdateResDTO;
import com.tibame.group1.db.entity.ProductCategoryEntity;

import java.io.IOException;
import java.util.List;

public interface ProductCategoryService {

    ProductCategoryCreateResDTO productCategoryCreate(ProductCategoryCreateReqDTO req) throws IOException;

    List<ProductCategoryEntity> productCategoryGetAll() throws IOException;

    ProductCategoryUpdateResDTO productCategoryUpdate(ProductCategoryUpdateReqDTO req) throws IOException;

//    ProductGetOneResDTO productGetOne(ProductGetOneReqDTO req) throws CheckRequestErrorException;

}
