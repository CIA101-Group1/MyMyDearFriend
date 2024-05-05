package com.tibame.group1.web.service;

import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.db.entity.ProductImgEntity;
import com.tibame.group1.web.dto.*;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductCreateResDTO productCreate(ProductCreateReqDTO req) throws IOException;

    List<ProductEntity> productGetAll() throws IOException;

    ProductUpdateResDTO productUpdate(ProductUpdateReqDTO req) throws IOException;

//    ProductGetOneResDTO productGetOne(ProductGetOneReqDTO req) throws CheckRequestErrorException;

    /**  productImg */

    ProductImgCreateResDTO productImgCreate(ProductImgCreateReqDTO req) throws IOException;

    List<ProductImgEntity> productImgGetAll() throws IOException;

    ProductImgUpdateResDTO productImgUpdate(ProductImgUpdateReqDTO req) throws IOException;

}
