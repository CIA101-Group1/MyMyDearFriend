package com.tibame.group1.admin.service.impl;

import com.tibame.group1.admin.dto.ProductCategoryCreateReqDTO;
import com.tibame.group1.admin.dto.ProductCategoryCreateResDTO;
import com.tibame.group1.admin.dto.ProductCategoryUpdateReqDTO;
import com.tibame.group1.admin.dto.ProductCategoryUpdateResDTO;
import com.tibame.group1.admin.service.ProductCategoryService;
import com.tibame.group1.db.entity.ProductCategoryEntity;
import com.tibame.group1.db.repository.ProductCategoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  //
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

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
//
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

}
