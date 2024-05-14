package com.tibame.group1.web.controller;

import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.web.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("mmdf/web/")
@Controller
@RequestMapping("/")
public class ProductFrontendController {

    @GetMapping("product/create")
    public String productCreate() {
        return "/product/product-create"; // 要導入的html
    }

    @GetMapping("product/1")
    public String productPage() {return "/product/product-page";
    }

    @GetMapping("product/select")
    public String productGetAll() {return "/product/product-select"; }
    public String queryGetAll() {return "/product/product-select"; }

//    @GetMapping("product/update")
//    public String productUpdate() {return "/product/product-update"; // 要導入的html
//    }

//    @GetMapping("product/query")
//    public String queryGetAll() {
//        return "/product/product-select"; // 要導入的html
//    }

    @Autowired
    private ProductService productService;
    @GetMapping("/product/update/{productId}")
    public String updateProductForm(@PathVariable("productId") String productId, Model model) {
        ProductEntity productEntity = productService.getOneProduct(Integer.valueOf(productId));
        model.addAttribute("productEntity", productEntity);
        return "/product/product-update";
    }

}
