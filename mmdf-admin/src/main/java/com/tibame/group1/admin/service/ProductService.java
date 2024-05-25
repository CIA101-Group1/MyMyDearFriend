package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.AdminLoginSourceDTO;
import com.tibame.group1.db.dto.*;
import com.tibame.group1.db.entity.ProductCategoryEntity;
import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.db.entity.ProductImgEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface ProductService {

    List<ProductCategoryEntity> productCategoryGetAll() throws IOException;


    List<ProductEntity> productGetAll();


    List<ProductImgEntity> productImgGetAll() throws IOException;

    List<ProductEntity> getAll();

    //
    List<ProductCategoryEntity> getAllCategory();

    List<ProductImgEntity> getAllProductImg();

    List<ProductEntity> queryGetAll(ProductQueryReqDTO productQueryReqDTO);

    //
    Page<ProductEntity> productGetAll(Pageable pageable);

    //
    HashMap<Integer, String> getProductReviewStatusList();

    //
    HashMap<Integer, String> getProductStatusList();


    void updateReviewStatus(int productId, String reviewStatus) throws Exception;

    void updateProductStatus(int productId, String reviewStatus) throws Exception;


}