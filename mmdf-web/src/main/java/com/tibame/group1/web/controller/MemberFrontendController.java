package com.tibame.group1.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("mmdf/web/")
public class MemberFrontendController {

    @GetMapping("/index")
    public String welcome() {
        return "index";
    }

    @GetMapping("member/login")
    public String memberHello() {
        return "member-login";
    }
}
