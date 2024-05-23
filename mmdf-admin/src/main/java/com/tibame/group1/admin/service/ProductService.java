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

    /**
     * productCategory
     */

    ProductCategoryCreateResDTO productCategoryCreate(ProductCategoryCreateReqDTO req) throws IOException;

    List<ProductCategoryEntity> productCategoryGetAll() throws IOException;

    ProductCategoryUpdateResDTO productCategoryUpdate(ProductCategoryUpdateReqDTO req) throws IOException;

    /**
     * product
     */

    List<ProductEntity> productGetAll();

    //0517
    ProductUpdateResDTO productUpdate(ProductUpdateReqDTO req, AdminLoginSourceDTO adminLoginSource) throws IOException;

    /**
     * productImg
     */

    List<ProductImgEntity> productImgGetAll() throws IOException;


    ProductEntity getOneProduct(Integer productId);

    ProductCategoryEntity getOneCategory(Integer productId);

    ProductImgEntity getOneProductImg(Integer productId);


    List<ProductEntity> getAll();

    List<ProductCategoryEntity> getAllCategory();

    List<ProductImgEntity> getAllProductImg();

    List<ProductEntity> queryGetAll(ProductQueryReqDTO productQueryReqDTO);

    //    List<ProductEntity> buyerproductGetAll(ProductQueryReqDTO productQueryReqDTO);


//0515

    /**
     * 取得所有商品,以及商品分類與照片
     */
    Page<ProductEntity> productGetAll(Pageable pageable);

    /**
     * 取得商品審核狀態設定清單
     */
    HashMap<Integer, String> getProductReviewStatusList();


    /**
     * 取得商品上下架狀態設定清單
     */
    HashMap<Integer, String> getProductStatusList();

//    0517

    ProductEntity getOneSellerProduct(Integer productId);

    List<ProductImgEntity> getProductImgListByProductId(Integer productId);


    void updateReviewStatus(int productId, String reviewStatus) throws Exception;

    void updateProductStatus(int productId, String reviewStatus) throws Exception;


}