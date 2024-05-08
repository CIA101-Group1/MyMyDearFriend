package com.tibame.group1.web.service.impl;

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

@Service  //
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImgRepository productImgRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    /** productCategory */

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

    /** product */

    @Override
    public ProductCreateResDTO productCreate(ProductCreateReqDTO req, LoginSourceDTO loginSource) {
        ProductEntity product = new ProductEntity();
        product.setSellerId(loginSource.getMemberId());  //會員ID，從登入驗證碼取的會員ID
        product.setCategoryId(Integer.valueOf(req.getCategoryId()));
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(Integer.valueOf(req.getPrice()));
        product.setQuantity(Integer.valueOf(req.getQuantity()));
        if(Integer.parseInt(req.getReviewStatus()) >= 0 && Integer.parseInt(req.getReviewStatus()) <= 2 ) {  //競標狀態
            product.setReviewStatus(Integer.valueOf(req.getReviewStatus()));
        }
        if(Integer.parseInt(req.getProductStatus()) >= 0 && Integer.parseInt(req.getProductStatus()) <= 1 ) {
            product.setProductStatus(Integer.valueOf(req.getProductStatus()));
        }
        product = productRepository.save(product);
        ProductCreateResDTO resDTO = new ProductCreateResDTO();
        resDTO.setProductId(product.getProductId());
        return resDTO;
    }

    @Override
    public List<ProductEntity> productGetAll() {
        return productRepository.findAll();
    }

    @Override
    public ProductUpdateResDTO productUpdate(ProductUpdateReqDTO req, LoginSourceDTO loginSource) {  //條件判斷
        ProductEntity product = new ProductEntity();
        product.setSellerId(loginSource.getMemberId());  //會員ID，從登入驗證碼取的會員ID
        product.setCategoryId(Integer.valueOf(req.getCategoryId()));
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(Integer.valueOf(req.getPrice()));
        product.setQuantity(Integer.valueOf(req.getQuantity()));
        if(Integer.parseInt(req.getReviewStatus()) >= 0 && Integer.parseInt(req.getReviewStatus()) <= 2 ) {  //競標狀態
            product.setReviewStatus(Integer.valueOf(req.getReviewStatus()));
        }
        if(Integer.parseInt(req.getProductStatus()) >= 0 && Integer.parseInt(req.getProductStatus()) <= 1 ) {
            product.setProductStatus(Integer.valueOf(req.getProductStatus()));
        }
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


    /**  productImg */

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

}
