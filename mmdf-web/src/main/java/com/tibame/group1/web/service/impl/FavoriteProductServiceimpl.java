package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.FavoriteProductEntity;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.db.entity.ProductImgEntity;
import com.tibame.group1.db.repository.FavoriteProductRepository;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.db.repository.ProductImgRepository;
import com.tibame.group1.db.repository.ProductRepository;
import com.tibame.group1.web.dto.FavoriteProductReqDTO;
import com.tibame.group1.web.dto.FavoriteProductResDTO;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.FavoriteProductService;

import jakarta.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class FavoriteProductServiceimpl implements FavoriteProductService {

    @Autowired FavoriteProductRepository favoriteProductRepository;

    @Autowired MemberRepository memberRepository;

    @Autowired ProductRepository productRepository;

    @Autowired ProductImgRepository productImgRepository;

    @Override
    public String addProductToFavorite(FavoriteProductReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        // 調用getValidatedMember檢查會員是否存在
        MemberEntity member = getValidatedMember(loginSource);
        FavoriteProductEntity favoriteProduct = new FavoriteProductEntity();
        favoriteProduct.setMemberId(member.getMemberId());
        favoriteProduct.setProductId(req.getProductId());
        favoriteProductRepository.save(favoriteProduct);
        return "OK";
    }

    @Override
    public List<FavoriteProductResDTO> getProductToFavorite(LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        // 調用getValidatedMember檢查會員是否存在
        MemberEntity member = getValidatedMember(loginSource);

        List<FavoriteProductEntity> favoriteProductList = new ArrayList<>();
        List<FavoriteProductResDTO> resList = new ArrayList<>();

        favoriteProductList = favoriteProductRepository.findByMemberId(member.getMemberId());
        for (FavoriteProductEntity favoriteProduct : favoriteProductList) {
            FavoriteProductResDTO res = new FavoriteProductResDTO();
            // 檢查商品是否存在
            ProductEntity product = productRepository.findById(favoriteProduct.getProductId()).orElse(null);
            if(product == null){
                log.warn("查無商品 {}", favoriteProduct.getProductId());
                throw new CheckRequestErrorException("查無商品");
            }
            // 檢查賣家是否存在
            MemberEntity seller = memberRepository.findById(product.getSellerId()).orElse(null);
            if(seller == null){
                log.warn("查無賣家 {}",  product.getSellerId());
                throw new CheckRequestErrorException("查無賣家");
            }
            res.setProductId(favoriteProduct.getProductId());
            res.setProductName(product.getName());
            res.setPrice(product.getPrice());
            res.setSellerId(product.getSellerId());
            res.setSellerName(seller.getName());
            // 查詢商品圖片
            List<ProductImgEntity> images = productImgRepository.findByProductEntity_ProductId(product.getProductId());
            if(!images.isEmpty()){
                ProductImgEntity image = images.get(0);
                String imageBase64 = Base64.getEncoder().encodeToString(image.getImage());
                res.setImageBase64(imageBase64);
            }else{
                res.setImageBase64(null);
            }
            resList.add(res);
        }
        return resList;
    }

    @Override
    public String removeProductFromFavorite(LoginSourceDTO loginSource, Integer productId)
            throws CheckRequestErrorException {
        // 調用getValidatedMember檢查會員是否存在
        MemberEntity member = getValidatedMember(loginSource);
        favoriteProductRepository.deleteByMemberIdAndProductId(member.getMemberId(), productId);
        return "remove";
    }

    // 驗證會員是否存在
    private MemberEntity getValidatedMember(LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        MemberEntity member = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (member == null) {
            log.warn("查無會員資料 {}", loginSource.getMemberId());
            throw new CheckRequestErrorException("查無會員資料");
        }
        return member;
    }
}
