package com.tibame.group1.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class OrderFrontendController {

    @GetMapping("order/all")
    public String orderAll() {
        return "/order/order-all";
    }

    @GetMapping("order/detail")
    public String orderDetail() {
        return "/order/order-detail";
    }

    @GetMapping("order/checkout")
    public String checkout() {
        return "/order/order-checkout";
    }
}
