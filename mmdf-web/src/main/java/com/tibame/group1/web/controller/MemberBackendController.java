package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.web.*;
import com.tibame.group1.common.exception.AuthorizationException;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.MemberService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/")
@CacheConfig(cacheNames = "member", keyGenerator = "mmdfKeyGenerator")
public class MemberBackendController {

    @Autowired private MemberService memberService;

    @PostMapping("member/create")
    @CacheEvict(allEntries = true)
    public @ResponseBody ResDTO<MemberCreateResDTO> memberCreate(
            @Valid @RequestBody MemberCreateReqDTO req)
            throws CheckRequestErrorException, DateException, IOException {
        ResDTO<MemberCreateResDTO> res = new ResDTO<>();
        res.setData(memberService.memberCreate(req));
        return res;
    }

    @PostMapping("member/edit")
    @CheckLogin(isVerified = false)
    @CacheEvict(allEntries = true)
    public @ResponseBody ResDTO<MemberEditResDTO> memberEdit(
            @Valid @RequestBody MemberEditReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<MemberEditResDTO> res = new ResDTO<>();
        res.setData(memberService.memberEdit(req, loginSource));
        return res;
    }

    @GetMapping("member/detail")
    @CheckLogin(isVerified = false)
    @Cacheable
    public @ResponseBody ResDTO<MemberDetailResDTO> memberDetail(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<MemberDetailResDTO> res = new ResDTO<>();
        res.setData(memberService.memberDetail(loginSource));
        return res;
    }

    @PostMapping("member/login")
    public @ResponseBody ResDTO<LoginResDTO> memberLogin(@Valid @RequestBody LoginReqDTO req)
            throws IOException {
        ResDTO<LoginResDTO> res = new ResDTO<>();
        res.setData(memberService.memberLogin(req));
        return res;
    }

    @PostMapping("member/verify")
    @CacheEvict(allEntries = true)
    public @ResponseBody ResDTO<MemberVerifyResDTO> memberVerify(
            @Valid @RequestBody MemberVerifyReqDTO req) throws AuthorizationException {
        ResDTO<MemberVerifyResDTO> res = new ResDTO<>();
        res.setData(memberService.memberVerify(req));
        return res;
    }

    @GetMapping("member/sendVerifyEmail")
    @CheckLogin(isVerified = false)
    @CacheEvict(allEntries = true)
    public @ResponseBody ResDTO<SendVerifyEmailResDTO> memberSendVerifyEmail(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<SendVerifyEmailResDTO> res = new ResDTO<>();
        res.setData(memberService.sendVerifyEmail(loginSource));
        return res;
    }

    @PostMapping("member/cidForget")
    @CacheEvict(allEntries = true)
    public @ResponseBody ResDTO<MemberCidForgetResDTO> memberCidForget(
            @Valid @RequestBody MemberCidForgetReqDTO req) {
        ResDTO<MemberCidForgetResDTO> res = new ResDTO<>();
        res.setData(memberService.memberCidForget(req));
        return res;
    }

    @PostMapping("member/cidReset")
    @CacheEvict(allEntries = true)
    public @ResponseBody ResDTO<MemberCidResetResDTO> memberCidReset(
            @Valid @RequestBody MemberCidResetReqDTO req) throws IOException {
        ResDTO<MemberCidResetResDTO> res = new ResDTO<>();
        res.setData(memberService.memberCidReset(req));
        return res;
    }
}
