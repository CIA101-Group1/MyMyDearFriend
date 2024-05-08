package com.tibame.group1.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("mmdf/web/")
@Controller
@RequestMapping("/")
public class ProductFrontendController {

    @GetMapping("product/list")
    public String productCreate() {
        return "product-list"; // 要導入的html
    }

    @GetMapping("product/select")
    public String productselect() {
        return "product-select";
    }

    //    @PostMapping(value = "ProductController", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public String receiveMessage(@RequestBody Map<String, String> json) {
//        String message = json.get("message");
//        System.out.println(message);  // 輸出 "Hello, Controller!"
//        return "訊息已收到！";
//    }

}
