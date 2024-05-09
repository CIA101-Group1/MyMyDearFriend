package com.tibame.group1.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("mmdf/web/")
@Controller
@RequestMapping("/")
public class ProductFrontendController {

    @GetMapping("product/create")
    public String productCreate() {
        return "product-create"; // 要導入的html
    }

    @GetMapping("product/select")
    public String productselect() {
        return "product-select";
    }


}
