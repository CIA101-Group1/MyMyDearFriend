package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>, JpaSpecificationExecutor<ProductEntity> {

    @Query("SELECT p FROM ProductEntity p WHERE p.categoryId = :categoryId")
    List<ProductEntity> findAllByCategoryId(@Param("categoryId") Integer categoryId);

    // Query 第一種寫法 ProductServiceImpl.java
    @Query("SELECT p FROM ProductEntity p " +
            "WHERE (:name IS NULL OR p.name LIKE CONCAT('%', :name, '%')) " +
            "AND (:description IS NULL OR p.description LIKE CONCAT('%', :description, '%'))" +
            "AND (:categoryId IS NULL OR p.categoryId = :categoryId)" +
            "AND (:reviewStatus IS NULL OR p.reviewStatus = :reviewStatus)" +
            "AND (:productStatus IS NULL OR p.productStatus = :productStatus)")
    List<ProductEntity> findProductsByQuery(
            @Param("name") String name, @Param("description") String description,
            @Param("categoryId") Integer categoryId, @Param("reviewStatus") Integer reviewStatus,
            @Param("productStatus") Integer productStatus);

// Query 第二種寫法 ProductServiceImpl.java
//    List<ProductEntity> findByNameLikeAndDescriptionLikeAndCategoryIdAndReviewStatusAndProductStatus(String name, String description, Integer categoryId, Integer reviewStatus, Integer productStatus);

// Query 第三種寫法 ProductServiceImpl.java

//      default List<ProductEntity> findProductsByQuery(String name, Integer categoryId) {
//
//          Specification<ProductEntity> spec = Specification.where(null);
//          if (StringUtils.isNotEmpty(name)) {
//              spec = spec.and((root, query, criteriaBuilder) ->
//                      criteriaBuilder.like(root.get("name"), "%" + name + "%"));
//          }
//          if (categoryId != null) {
//              spec = spec.and((root, query, criteriaBuilder) ->
//                      criteriaBuilder.equal(root.get("categoryId"), categoryId));
//          }
//
//          return findAll(spec);
//      }


}
