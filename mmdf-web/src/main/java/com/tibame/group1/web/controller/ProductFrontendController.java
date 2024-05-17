package com.tibame.group1.web.controller;

import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.db.entity.ProductCategoryEntity;
import com.tibame.group1.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProductFrontendController {

    @Autowired
    private ProductService productService;

//    @PostMapping ("/product/getOne/{productId}")
//    public String updateProductForm(@PathVariable("productId") String productId, Model model) {
//
//        ProductEntity productEntity = productService.getOneProduct(Integer.valueOf(productId));
//        model.addAttribute("productEntity", productEntity);
//
//        return "/product/product-update";
//    }

//    0515
//    buyer

    @GetMapping("product/buyer/select")
    public String getProduct(Model model,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size) {
        {
            //BackEndController
            Page<ProductEntity> productPage = productService.productGetAll(PageRequest.of(page, size));
            model.addAttribute("productPage", productPage);

            HashMap<Integer, String> reviewStatusList = productService.getProductReviewStatusList();
            model.addAttribute("reviewStatusList", reviewStatusList);

            HashMap<Integer, String> productStatusList = productService.getProductStatusList();
            model.addAttribute("productStatusList", productStatusList);

        return "/product/buyer-product-select";
        }
    }

//    seller

    @GetMapping("product/seller/select")
    public String productshop(Model model,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {
        {
            Page<ProductEntity> productPage = productService.productGetAll(PageRequest.of(page, size));
            model.addAttribute("productPage", productPage);

            HashMap<Integer, String> reviewStatusList = productService.getProductReviewStatusList();
            model.addAttribute("reviewStatusList", reviewStatusList);

            HashMap<Integer, String> productStatusList = productService.getProductStatusList();
            model.addAttribute("productStatusList", productStatusList);
            return "/product/seller-product-select";
        }
    }

//    0517

    @GetMapping("product/seller/create")
    public String ProductCreate(Model model) {
        List<ProductCategoryEntity> productCategoryList =productService. getAllCategory();
        model.addAttribute("productCategoryList", productCategoryList);
        return "/product/seller-product-create"; // 要導入的html
    }

}
