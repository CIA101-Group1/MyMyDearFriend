package com.tibame.group1.web.service.impl;

import com.tibame.group1.db.entity.ProductEntity;
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

    @Override
    public ProductCreateResDTO productCreate(ProductCreateReqDTO req) {
        ProductEntity product = new ProductEntity();
        product.setSellerId(req.getMemberId());  //會員ID，從登入驗證碼取的會員ID
        product.setCategoryId(req.getCategoryId());
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setQuantity(req.getQuantity());
        product.setReviewStatus(req.getReviewStatus());
        product.setProductStatus(req.getProductStatus());
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
    public ProductUpdateResDTO productUpdate(ProductUpdateReqDTO req) {
        ProductEntity product = new ProductEntity();
        product.setSellerId(req.getMemberId());  //會員ID，從登入驗證碼取的會員ID
        product.setCategoryId(req.getCategoryId());
        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        product.setQuantity(req.getQuantity());
        product.setReviewStatus(req.getReviewStatus());
        product.setProductStatus(req.getProductStatus());
        product = productRepository.save(product);
        ProductUpdateResDTO resDTO = new ProductUpdateResDTO();
        resDTO.setProductId(product.getProductId());
        return resDTO;
    }


}
