package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.ProductImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImgEntity, Integer> {

    //    0517
    List<ProductImgEntity> findByProductEntity_ProductId(Integer productId);
}
