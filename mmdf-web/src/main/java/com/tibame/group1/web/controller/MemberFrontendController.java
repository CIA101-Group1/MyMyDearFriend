package com.tibame.group1.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class MemberFrontendController {

    @GetMapping("/test-index")
    public String index() {
        return "test-index";
    }

    @GetMapping("member/login")
    public String memberLogin() {
        return "member-login";
    }

    @GetMapping("member/detail")
    public String memberDetail() {
        return "member-detail";
    }

    @GetMapping("member/create")
    public String memberCreate() {
        return "member-create";
    }

    @GetMapping("member/verify")
    public String memberVerify() {
        return "member-verify";
    }
}
