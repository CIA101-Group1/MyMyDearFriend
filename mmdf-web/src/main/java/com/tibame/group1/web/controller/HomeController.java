package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @GetMapping({"/", "/home"})
    public String getHomePage(Model model) {
        return "home";
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/test")
    public String getMemberHomePage() {
        return "member/member-home";
    }

}
