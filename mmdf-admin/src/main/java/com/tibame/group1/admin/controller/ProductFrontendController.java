package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.service.ProductService;
import com.tibame.group1.db.entity.ProductCategoryEntity;
import com.tibame.group1.db.entity.ProductEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
public class ProductFrontendController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/review")
    public String adminReviewProduct(Model model,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        {
            List<ProductCategoryEntity> productCategoryList = productService.getAllCategory();
            model.addAttribute("productCategoryList", productCategoryList);

            Page<ProductEntity> productPage = productService.productGetAll(PageRequest.of(page, size));
            model.addAttribute("productPage", productPage);

            HashMap<Integer, String> reviewStatusList = productService.getProductReviewStatusList();
            model.addAttribute("reviewStatusList", reviewStatusList);

            HashMap<Integer, String> productStatusList = productService.getProductStatusList();
            model.addAttribute("productStatusList", productStatusList);

            return "/product-admin/admin-product-review";
        }
    }


    @GetMapping("product/seller/status")
    public String productstatus(Model model,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {
        {
            List<ProductCategoryEntity> productCategoryList = productService.getAllCategory();
            model.addAttribute("productCategoryList", productCategoryList);

            Page<ProductEntity> productPage = productService.productGetAll(PageRequest.of(page, size));
            model.addAttribute("productPage", productPage);

            HashMap<Integer, String> reviewStatusList = productService.getProductReviewStatusList();
            model.addAttribute("reviewStatusList", reviewStatusList);

            HashMap<Integer, String> productStatusList = productService.getProductStatusList();
            model.addAttribute("productStatusList", productStatusList);
            return "/product/seller-product-status";
        }
    }


}
