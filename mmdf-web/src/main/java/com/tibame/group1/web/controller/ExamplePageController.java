package com.tibame.group1.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/example")
public class ExamplePageController {

    @GetMapping("/example")
    public String example() {
        return "/example/example";
    }

    @GetMapping("/404")
    public String error404() {
        return "/example/404";
    }

    @GetMapping("/login")
    public String login() {
        return "/example/login";
    }

    @GetMapping("/account")
    public String account() {
        return "/example/account";
    }

    @GetMapping("/cart")
    public String cart() {
        return "/example/cart";
    }

    @GetMapping("/wishlist")
    public String wishlist() {
        return "/example/wishlist";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "/example/checkout";
    }

    @GetMapping("/blog")
    public String blog() {
        return "/example/blog-list";
    }

    @GetMapping("/blog-detail")
    public String blogDetail() {
        return "/example/blog-detail";
    }

    @GetMapping("/shop")
    public String shop() {
        return "/example/shop";
    }

    @GetMapping("/shop-detail")
    public String shopDetail() {
        return "/example/shop-detail";
    }

    @GetMapping("/service")
    public String service() {
        return "/example/service";
    }

    @GetMapping("/contact")
    public String contact() {
        return "/example/contact";
    }

    @GetMapping("/faq")
    public String faq() {
        return "/example/faq";
    }
}
