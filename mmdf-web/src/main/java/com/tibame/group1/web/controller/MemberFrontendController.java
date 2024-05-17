package com.tibame.group1.web.controller;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@CacheConfig(cacheNames = "member", keyGenerator = "mmdfKeyGenerator")
public class MemberFrontendController {

    @GetMapping("member/home")
    @Cacheable
    public String memberHome() {
        return "/member/member-home";
    }

    @GetMapping("member/login")
    @Cacheable
    public String memberLogin() {
        return "/member/member-login";
    }

    @GetMapping("member/detail")
    @Cacheable
    public String memberDetail() {
        return "/member/member-detail";
    }

    @GetMapping("member/create")
    @Cacheable
    public String memberCreate() {
        return "/member/member-create";
    }

    @GetMapping("member/verify")
    @Cacheable
    public String memberVerify() {
        return "/member/member-verify";
    }

    @GetMapping("member/cidForget")
    @Cacheable
    public String memberCidForget() {
        return "/member/member-cid-forget";
    }

    @GetMapping("member/cidReset")
    @Cacheable
    public String memberCidReset() {
        return "/member/member-cid-reset";
    }
}
