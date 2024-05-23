package com.tibame.group1.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MemberNoticeFrontendController {

    @GetMapping("memberNotice/all")
    public String memberHome() {
        return "/member/member-notice";
    }
}
