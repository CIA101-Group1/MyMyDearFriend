package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.dto.web.LoginSourceDTO;
import com.tibame.group1.common.utils.ConvertUtils;
import com.tibame.group1.common.utils.StringUtils;
import com.tibame.group1.db.entity.ProductCategoryEntity;
import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.db.entity.ProductImgEntity;
import com.tibame.group1.db.repository.ProductCategoryRepository;
import com.tibame.group1.db.repository.ProductImgRepository;
import com.tibame.group1.db.repository.ProductRepository;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
//    public List<ProductCompoundResDTO> findProductsByCompound(
//            String productId, String sellerId, String categoryId, String name,
//            String description, String price, String reviewStatus, String productStatus
////            , ProductCategoryEntity productCategory, MemberEntity memberEntity
//    ) {
//        // 根據指定的條件查詢產品資料
//        List<ProductEntity> products = productRepository.findByCompound(
//                productId, sellerId, categoryId, name, description, price,
//                reviewStatus, productStatus
////                , productCategory, memberEntity
//        );
//
//        // 將 Entity 轉換為 DTO
//        return products.stream()
//                .map(this::productCompoundDTO)
//                .collect(Collectors.toList());
//    }
//
//    private ProductCompoundResDTO productCompoundDTO(ProductEntity product) {
//        ProductCompoundResDTO dto = new ProductCompoundResDTO();
//        dto.setProductId(String.valueOf(product.getProductId()));
//        dto.setSellerId(String.valueOf(product.getSellerId()));
//        dto.setCategoryId(String.valueOf(product.getCategoryId()));
//        dto.setName(product.getName());
//        dto.setDescription(product.getDescription());
//        dto.setPrice(String.valueOf(product.getPrice()));
//        dto.setReviewStatus(String.valueOf(product.getReviewStatus()));
//        dto.setProductStatus(String.valueOf(product.getProductStatus()));
//
//        ProductCategoryEntity productCategory = new ProductCategoryEntity();
//        productCategory.setCategoryId(product.getCategoryId());
//
//        MemberEntity memberEntity = new MemberEntity();
//        memberEntity.setMemberId(memberEntity.getMemberId());
//
//        return dto;
//    }

    @Override
    public ProductUpdateResDTO productUpdate(ProductUpdateReqDTO req, LoginSourceDTO loginSource) {  //條件判斷
        ProductEntity product = new ProductEntity();
        product.setSellerId(loginSource.getMemberId());  //會員ID，從登入驗證碼取的會員ID
        product.setCategoryId(Integer.valueOf(req.getCategoryId()));
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(Integer.valueOf(req.getPrice()));
        product.setQuantity(Integer.valueOf(req.getQuantity()));
        product = productRepository.save(product);
        ProductUpdateResDTO resDTO = new ProductUpdateResDTO();
        resDTO.setProductId(product.getProductId());
        return resDTO;
    }

//    @Override
//    public ProductGetOneResDTO productGetOne(ProductGetOneReqDTO req) {
//        ProductEntity product = new ProductEntity();
//        product.setSellerId(req.getMemberId());  //會員ID，從登入驗證碼取的會員ID
//        product.setCategoryId(req.getCategoryId());
//        product.setName(req.getName());
//        product.setDescription(req.getDescription());
//        product.setPrice(req.getPrice());
//        product.setQuantity(req.getQuantity());
//        product.setReviewStatus(req.getReviewStatus());
//        product.setProductStatus(req.getProductStatus());
//        product = productRepository.save(product);
//        ProductGetOneResDTO resDTO = new ProductGetOneResDTO();
//        resDTO.setProductId(product.getProductId());
//        return (ProductGetOneResDTO) resDTO.getProductGetAllResDTO();
//    }
//    @Override
//    public List<ProductGetOneResDTO>  productGetOne() { return productRepository.findById(productGetOne().get(ProductGetOneReqDTO));
//    }
//    public List<ProductEntity> productGetOne() {
//        return productRepository.findById(productGetOne().indexOf(Integer));
//    }
//    public ProductEntity productGetOne(Integer productid) {
//        return productRepository.findById(productid).orElse(null);
//    }


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
        //        return empRepository.findAll();
        List<ProductEntity> result = productRepository.findAll();
        return result;
    }

    @Override
    public List<ProductCategoryEntity> getAllCategory() {
        //        return empRepository.findAll();
        List<ProductCategoryEntity> result = productCategoryRepository.findAll();
        return result;
    }

    @Override
    public List<ProductImgEntity> getAllProductImg() {
        //        return empRepository.findAll();
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

}
