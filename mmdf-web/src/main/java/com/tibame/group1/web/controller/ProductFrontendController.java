package com.tibame.group1.web.controller;

import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.db.entity.ProductCategoryEntity;
import com.tibame.group1.web.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                             @RequestParam(value = "size", defaultValue = "12") int size) {
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
            List<ProductCategoryEntity> productCategoryList = productService.getAllCategory();
            model.addAttribute("productCategoryList", productCategoryList);

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
        List<ProductCategoryEntity> productCategoryList = productService.getAllCategory();
        model.addAttribute("productCategoryList", productCategoryList);
        return "/product/seller-product-create"; // 要導入的html
    }

//    @GetMapping("product/seller/update")
//    public String ProductUpdate(Model model) {
//        List<ProductCategoryEntity> productCategoryList =productService. getAllCategory();
//        model.addAttribute("productCategoryList", productCategoryList);
//        return "/product/seller-product-update"; // 要導入的html
//    }

    /**
     * 透過商品ID取得單一商品，進入修改畫面
     */
    @GetMapping("/seller/product/getOne/{productId}")
    public String getOneProduct(@PathVariable("productId") Integer productId, Model model) {
        ProductEntity productEntity = productService.getOneSellerProduct(productId);
        model.addAttribute("productEntity", productEntity);

        List<ProductCategoryEntity> productCategoryList = productService.getAllCategory();
        model.addAttribute("productCategoryList", productCategoryList);

        return "/product/seller-product-getOne-update";
    }


//    0518 my

    @GetMapping("/buyer/product/getOne/{productId}")
    public String getOneBuyerProduct(@PathVariable("productId") String productId, Model model) {

        ProductEntity productEntity = productService.getOneProduct(Integer.valueOf(productId));
        model.addAttribute("productEntity", productEntity);

        List<ProductCategoryEntity> productCategoryList = productService.getAllCategory();
        model.addAttribute("productCategoryList", productCategoryList);

        return "/product/buyer-product-getOne";
    }

    @GetMapping("/admin/product/review")
    public String adminReviewProduct(Model model,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "10") int size) {
        {
            Page<ProductEntity> productPage = productService.productGetAll(PageRequest.of(page, size));
            model.addAttribute("productPage", productPage);

            HashMap<Integer, String> reviewStatusList = productService.getProductReviewStatusList();
            model.addAttribute("reviewStatusList", reviewStatusList);

            HashMap<Integer, String> productStatusList = productService.getProductStatusList();
            model.addAttribute("productStatusList", productStatusList);

            return "/product/admin-product-review";
        }
    }

    @GetMapping("/buyer/proCategory/getOne/{categoryId}")
    public String getOneByCategory(@PathVariable("categoryId") String productId, Model model) {

        ProductEntity productEntity = productService.getOneProduct(Integer.valueOf(productId));
        model.addAttribute("productEntity", productEntity);

        List<ProductCategoryEntity> productCategoryList = productService.getAllCategory();
        model.addAttribute("productCategoryList", productCategoryList);

        return "/product/buyer-proCategory-select";
    }


    @GetMapping("api/destination")
    public ResponseEntity<String> redirect(HttpServletRequest request) {
        System.out.println("收到redirect");
        return null;
    }


    @GetMapping("/admin/pro/review")
    public String adminReview(Model model,
                              @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size) {
        {
            Page<ProductEntity> productPage = productService.productGetAll(PageRequest.of(page, size));
            model.addAttribute("productPage", productPage);

            HashMap<Integer, String> reviewStatusList = productService.getProductReviewStatusList();
            model.addAttribute("reviewStatusList", reviewStatusList);

            HashMap<Integer, String> productStatusList = productService.getProductStatusList();
            model.addAttribute("productStatusList", productStatusList);

            return "/product/index";
        }
    }
}
