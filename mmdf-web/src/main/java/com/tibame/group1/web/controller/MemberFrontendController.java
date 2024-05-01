package com.tibame.group1.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mmdf/web/")
public class MemberFrontendController {

    @GetMapping("member/login")
    public String memberLogin(Model model) {
        return "member-login"; // 要導入的html
    }

    @GetMapping("member/detail")
    public String memberLogin(
            Model model,
            @RequestParam("memberAccount") String memberAccount,
            @RequestParam("cid") String cid) {
        return "member-detail"; // 要導入的html
    }
}
