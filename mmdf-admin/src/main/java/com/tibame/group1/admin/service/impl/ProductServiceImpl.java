package com.tibame.group1.admin.service.impl;

import com.tibame.group1.admin.service.NoticeService;
import com.tibame.group1.admin.service.ProductService;
import com.tibame.group1.db.dto.*;
import com.tibame.group1.db.entity.*;
import com.tibame.group1.db.repository.ProductCategoryRepository;
import com.tibame.group1.db.repository.ProductImgRepository;
import com.tibame.group1.db.repository.ProductRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProductServiceImpl implements ProductService {

//    @Autowired
//    private NoticeService noticeService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImgRepository productImgRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ProductCategoryEntity> productCategoryGetAll() {
        return productCategoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductEntity> productGetAll() {
        List<ProductEntity> result = productRepository.findAll();
        return result;
    }

//    0517


    /**
     * productImg
     */

    @Transactional(readOnly = true)
    @Override
    public List<ProductImgEntity> productImgGetAll() {
        return productImgRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductEntity> getAll() {
        List<ProductEntity> result = productRepository.findAll();
        return result;
    }

    @Override
    public List<ProductCategoryEntity> getAllCategory() {
        List<ProductCategoryEntity> result = productCategoryRepository.findAll();
        return result;
    }

    @Override
    public List<ProductImgEntity> getAllProductImg() {
        List<ProductImgEntity> result = productImgRepository.findAll();
        return result;
    }
    @Transactional(readOnly = true)
    @Override
    public List<ProductEntity> queryGetAll(ProductQueryReqDTO productQueryReqDTO) {
        // Query 第一、三種寫法 ProductRepository.java
        return productRepository.findProductsByQuery(
                productQueryReqDTO.getName(), productQueryReqDTO.getDescription(), productQueryReqDTO.getCategoryId(),
                productQueryReqDTO.getReviewStatus(), productQueryReqDTO.getProductStatus()
        );
    }

    //0515 //
    @Transactional(readOnly = true)
    @Override
    public Page<ProductEntity> productGetAll(Pageable pageable) {
        //1.查所有商品
        Page<ProductEntity> productPage = productRepository.findAll(pageable);
        log.info("productPage: {}", productPage);

        //2.查商品分類
        List<ProductCategoryEntity> productCategoryList = getAllCategory();
        //2-1.用map加快比較效率不要用雙迴圈
        Map<Integer, ProductCategoryEntity> categoryMap = productCategoryList.stream()
                .collect(Collectors.toMap(ProductCategoryEntity::getCategoryId, Function.identity()));
        //2-2.放入商品集合
        productPage.getContent().forEach(productEntity -> {
            Integer categoryId = productEntity.getCategoryId();
            ProductCategoryEntity matchedCategory = categoryMap.get(categoryId);
            if (matchedCategory != null) {
                productEntity.setProductCategoryEntity(matchedCategory);
            }
        });

        //3.查商品照片
        List<ProductImgEntity> productImgEntityList = getAllProductImg();
        //3-1.如果照片不是空值,轉成base64
        productImgEntityList.stream()
                .filter(productImgEntity -> productImgEntity.getImage() != null)
                .forEach(productImgEntity -> {
                    byte[] image = productImgEntity.getImage();
                    String base64 = Base64.getEncoder().encodeToString(image);
                    productImgEntity.setImageBase64(base64);
                });
        //3-2.用map加快比較效率不要用雙迴圈
        Map<Integer, List<ProductImgEntity>> productImgMap = productImgEntityList.stream()
                .filter(productImgEntity -> productImgEntity.getProductId() != null)
                .collect(Collectors.groupingBy(ProductImgEntity::getProductId));
        //3-3.放入商品集合
        productPage.getContent().forEach(productEntity -> {
            Integer productId = productEntity.getProductId();
            List<ProductImgEntity> matchedProductImg = productImgMap.get(productId);
            if (matchedProductImg != null) {
                Set<ProductImgEntity> productImgs = new HashSet<>(matchedProductImg);
                productEntity.setProductImgs(productImgs);
            }
        });
        return productPage;
    }


    @Override
    public HashMap<Integer, String> getProductReviewStatusList() {
        HashMap<Integer, String> result = new HashMap<>();
        result.put(0, "審核中");
        result.put(1, "通過");
        result.put(2, "未通過");
        return result;
    }

    @Override
    public HashMap<Integer, String> getProductStatusList() {
        HashMap<Integer, String> result = new HashMap<>();
        result.put(0, "下架");
        result.put(1, "上架");
        return result;
    }

    @Override
    public void updateReviewStatus(int productId, String reviewStatus) throws Exception {
        // 根据产品ID从数据库中获取产品对象
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found with id: " + productId));

//        MemberEntity member = new MemberEntity();
//        noticeService.memberNoticeCreate(member, MemberNoticeEntity.NoticeCategory.SYSTEM, "註冊成功", "完成註冊會員", true);

        // 更新产品的审核状态 ，上架
        product.setReviewStatus(Integer.valueOf(reviewStatus));

        // 保存更新后的产品对象回数据库
        productRepository.save(product);
    }

    @Override
    public void updateProductStatus(int productId, String productStatus) throws Exception {
        // 根据产品ID从数据库中获取产品对象
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found with id: " + productId));

        // 更新产品的审核状态 ，上架
        product.setProductStatus(Integer.valueOf(productStatus));

        // 保存更新后的产品对象回数据库
        productRepository.save(product);
    }

}
