package com.tibame.group1.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {
    @GetMapping({"/", "/home"})
    public String getHomePage(Model model) {
        return "home";
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
