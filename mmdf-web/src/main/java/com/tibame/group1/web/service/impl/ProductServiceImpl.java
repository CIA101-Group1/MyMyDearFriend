package com.tibame.group1.web.service.impl;

import com.tibame.group1.db.dto.*;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.common.utils.ConvertUtils;
import com.tibame.group1.common.utils.StringUtils;
import com.tibame.group1.db.entity.ProductCategoryEntity;
import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.db.entity.ProductImgEntity;
import com.tibame.group1.db.repository.ProductCategoryRepository;
import com.tibame.group1.db.repository.ProductImgRepository;
import com.tibame.group1.db.repository.ProductRepository;
import com.tibame.group1.web.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImgRepository productImgRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    /**
     * productCategory
     */

    @Override
    public ProductCategoryCreateResDTO productCategoryCreate(ProductCategoryCreateReqDTO req) {
        ProductCategoryEntity product = new ProductCategoryEntity();
        product.setCategoryName(req.getCategoryName());  //會員ID，從登入驗證碼取的會員ID
        product = productCategoryRepository.save(product);
        ProductCategoryCreateResDTO resDTO = new ProductCategoryCreateResDTO();
        resDTO.setCategoryId(product.getCategoryId());
        return resDTO;
    }

    @Override
    public List<ProductCategoryEntity> productCategoryGetAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public ProductCategoryUpdateResDTO productCategoryUpdate(ProductCategoryUpdateReqDTO req) {  //條件判斷
        ProductCategoryEntity product = new ProductCategoryEntity();
        product.setCategoryId(req.getCategoryId());
        product.setCategoryName(req.getCategoryName());
        product = productCategoryRepository.save(product);
        ProductCategoryUpdateResDTO resDTO = new ProductCategoryUpdateResDTO();
        resDTO.setCategoryId(product.getCategoryId());
        return resDTO;
    }

    /**
     * product
     */

    @Override
    public ProductCreateResDTO productCreate(ProductCreateReqDTO req, LoginSourceDTO loginSource) {

        ProductEntity product = new ProductEntity();
        product.setSellerId(loginSource.getMemberId());  //會員ID，從登入驗證碼取的會員ID
        product.setCategoryId(Integer.valueOf(req.getCategoryId()));
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(Integer.valueOf(req.getPrice()));
        product.setQuantity(Integer.valueOf(req.getQuantity()));
        product.setReviewStatus(0);
        product.setProductStatus(0);
        product = productRepository.save(product);

//     productImg
        ProductImgEntity productImg = new ProductImgEntity();
        productImg.setImage(StringUtils.isEmpty(req.getImage()) ? null : ConvertUtils.base64ToBytes(req.getImage()));
        productImg.setProductId(product.getProductId());

        productImgRepository.save(productImg);

        ProductCreateResDTO resDTO = new ProductCreateResDTO();
        resDTO.setProductId(product.getProductId());
        return resDTO;

    }

    @Override
    public List<ProductEntity> productGetAll() {
        List<ProductEntity> result = productRepository.findAll();
        return result;
    }

//    @Override
//    public ProductUpdateResDTO productUpdate(ProductUpdateReqDTO req, LoginSourceDTO loginSource) {  //條件判斷
//        ProductEntity product = new ProductEntity();
//        product.setSellerId(loginSource.getMemberId());  //會員ID，從登入驗證碼取的會員ID
//        product.setCategoryId(Integer.valueOf(req.getCategoryId()));
//        product.setName(req.getName());
//        product.setDescription(req.getDescription());
//        product.setPrice(Integer.valueOf(req.getPrice()));
//        product.setQuantity(Integer.valueOf(req.getQuantity()));
//        product.setProductId(req.getProductId());
//        product = productRepository.save(product);
//        ProductUpdateResDTO resDTO = new ProductUpdateResDTO();
//        resDTO.setProductId(product.getProductId());
//        return resDTO;
//    }

    //    0517
    @Override
    public ProductUpdateResDTO productUpdate(ProductUpdateReqDTO req, LoginSourceDTO loginSource) {  //條件判斷
        ProductEntity product = new ProductEntity();
        product.setProductId(req.getProductId());
        product.setSellerId(loginSource.getMemberId());  //會員ID，從登入驗證碼取的會員ID
        product.setCategoryId(Integer.valueOf(req.getCategoryId()));
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(Integer.valueOf(req.getPrice()));
        product.setQuantity(Integer.valueOf(req.getQuantity()));
        product.setReviewStatus(0);
        product.setProductStatus(0);
        product = productRepository.save(product);

        if (req.getUpdateImg()) {
            List<ProductImgEntity> productImgEntityList = productImgRepository.findByProductEntity_ProductId(req.getProductId());
            if (productImgEntityList != null && !productImgEntityList.isEmpty()) {
                ProductImgEntity productImg = productImgEntityList.get(0);
                productImg.setImage(StringUtils.isEmpty(req.getImage()) ? null : ConvertUtils.base64ToBytes(req.getImage()));
                productImgRepository.save(productImg);
            }
        }
        ProductUpdateResDTO resDTO = new ProductUpdateResDTO();
        resDTO.setProductId(product.getProductId());
        return resDTO;
    }

    @Override
    public ProductEntity getOneSellerProduct(Integer productId) {

        //1.查所有商品
        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        //2.查商品分類
        ProductCategoryEntity productCategoryEntity = productCategoryRepository.findById(productEntity.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("ProductCategory with id " + productEntity.getCategoryId() + " not found"));
        //2-2.放入商品集合
        productEntity.setProductCategoryEntity(productCategoryEntity);

        //3.查商品照片
        List<ProductImgEntity> productImgEntityList = getProductImgListByProductId(productId);
        //3-1.如果照片不是空值,轉成base64
        productImgEntityList.stream()
                .filter(productImgEntity -> productImgEntity.getImage() != null)
                .forEach(productImgEntity -> {
                    byte[] image = productImgEntity.getImage();
                    String base64 = Base64.getEncoder().encodeToString(image);
                    productImgEntity.setImageBase64(base64);
                });
        // 將 List 轉換為 Set
        Set<ProductImgEntity> productImgEntitySet = new HashSet<>(productImgEntityList);
        //3-3.放入商品集合
        productEntity.setProductImgs(productImgEntitySet);

        return productEntity;
    }


    @Override
    public List<ProductImgEntity> getProductImgListByProductId(Integer productId) {
        return productImgRepository.findByProductEntity_ProductId(productId);
    }

//    0517


    /**
     * productImg
     */

    @Override
    public ProductImgCreateResDTO productImgCreate(ProductImgCreateReqDTO req, LoginSourceDTO loginSource) {
        ProductImgEntity productImg = new ProductImgEntity();
        productImg.setProductId(Integer.valueOf(req.getProductId()));
        productImg.setImage(req.getImage().getBytes());
        productImg = productImgRepository.save(productImg);
        ProductImgCreateResDTO resDTO = new ProductImgCreateResDTO();
        resDTO.setImageId(productImg.getImageId());
        return resDTO;
    }

    @Override
    public List<ProductImgEntity> productImgGetAll() {
        return productImgRepository.findAll();
    }

    @Override
    public ProductImgUpdateResDTO productImgUpdate(ProductImgUpdateReqDTO req, LoginSourceDTO loginSource) {  //條件判斷
        ProductImgEntity productImg = new ProductImgEntity();
        productImg.setProductId(Integer.valueOf(req.getProductId()));
        productImg.setImage(req.getImage().getBytes());
        productImg = productImgRepository.save(productImg);
        ProductImgUpdateResDTO resDTO = new ProductImgUpdateResDTO();
        resDTO.setImageId(productImg.getImageId());
        return resDTO;
    }

    /***/
    @Override
    public ProductEntity getOneProduct(Integer productId) {
        Optional<ProductEntity> optional = productRepository.findById(productId);
        return optional.orElse(null);
    }

    @Override
    public ProductCategoryEntity getOneCategory(Integer productId) {
        Optional<ProductCategoryEntity> optional = productCategoryRepository.findById(productId);
        return optional.orElse(null);
    }

    @Override
    public ProductImgEntity getOneProductImg(Integer productId) {
        Optional<ProductImgEntity> optional = productImgRepository.findById(productId);
        return optional.orElse(null);
    }

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

    @Override
    public List<ProductEntity> queryGetAll(ProductQueryReqDTO productQueryReqDTO) {
        // Query 第一、三種寫法 ProductRepository.java
        return productRepository.findProductsByQuery(
                productQueryReqDTO.getName(), productQueryReqDTO.getDescription(), productQueryReqDTO.getCategoryId(),
                productQueryReqDTO.getReviewStatus(), productQueryReqDTO.getProductStatus()
        );
        // Query 第二種寫法 ProductRepository.java
//        return productRepository.findByNameLikeAndDescriptionLikeAndCategoryIdAndReviewStatusAndProductStatus(
//                productQueryReqDTO.getName(), productQueryReqDTO.getDescription(), productQueryReqDTO.getCategoryId(),
//                productQueryReqDTO.getReviewStatus(), productQueryReqDTO.getProductStatus()
    }

    //0515
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
