package com.tibame.group1.web.service;

import com.tibame.group1.common.dto.web.LoginSourceDTO;
import com.tibame.group1.db.entity.ProductCategoryEntity;
import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.db.entity.ProductImgEntity;
import com.tibame.group1.web.dto.*;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    /** productCategory */

    ProductCategoryCreateResDTO productCategoryCreate(ProductCategoryCreateReqDTO req) throws IOException;

    List<ProductCategoryEntity> productCategoryGetAll() throws IOException;

    ProductCategoryUpdateResDTO productCategoryUpdate(ProductCategoryUpdateReqDTO req) throws IOException;

    /** product */

    ProductCreateResDTO productCreate(ProductCreateReqDTO req, LoginSourceDTO loginSource) throws IOException ;

    List<ProductEntity> productGetAll() throws IOException;

    ProductUpdateResDTO productUpdate(ProductUpdateReqDTO req, LoginSourceDTO loginSource) throws IOException;

//    ProductGetOneResDTO productGetOne(ProductGetOneReqDTO req) throws CheckRequestErrorException;

    /**  productImg */

    ProductImgCreateResDTO productImgCreate(ProductImgCreateReqDTO req, LoginSourceDTO loginSource) throws IOException;

    List<ProductImgEntity> productImgGetAll() throws IOException;

    ProductImgUpdateResDTO productImgUpdate(ProductImgUpdateReqDTO req, LoginSourceDTO loginSource) throws IOException;

}
