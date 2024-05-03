package com.tibame.group1.web.controller;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("mmdf/web/")
public class ProductFrontendController {

    @GetMapping("product/list")
    public String productlist(Model model) {
        return "product-list"; // 要導入的html
    }
    @PostMapping(value = "ProductController", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String receiveMessage(@RequestBody Map<String, String> json) {
        String message = json.get("message");
        System.out.println(message);  // 輸出 "Hello, Controller!"
        return "訊息已收到！";
    }
}
