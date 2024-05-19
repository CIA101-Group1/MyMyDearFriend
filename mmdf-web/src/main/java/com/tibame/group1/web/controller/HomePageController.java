package com.tibame.group1.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping({"/", "/home"})
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/example")
    public String hello() {
        return "example";
    }
}
