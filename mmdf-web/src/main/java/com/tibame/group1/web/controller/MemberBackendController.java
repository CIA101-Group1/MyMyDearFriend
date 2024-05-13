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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api/")
public class MemberBackendController {

    @Autowired private MemberService memberService;

    @PostMapping("member/create")
    public @ResponseBody ResDTO<MemberCreateResDTO> memberCreate(
            @Valid @RequestBody MemberCreateReqDTO req)
            throws CheckRequestErrorException, DateException, IOException {
        ResDTO<MemberCreateResDTO> res = new ResDTO<>();
        res.setData(memberService.memberCreate(req));
        return res;
    }

    @PostMapping("member/edit")
    @CheckLogin(isVerified = false)
    public @ResponseBody ResDTO<MemberEditResDTO> memberEdit(
            @Valid @RequestBody MemberEditReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<MemberEditResDTO> res = new ResDTO<>();
        res.setData(memberService.memberEdit(req, loginSource));
        return res;
    }

    @GetMapping("member/detail")
    @CheckLogin(isVerified = false)
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
    public @ResponseBody ResDTO<MemberVerifyResDTO> memberVerify(
            @Valid @RequestBody MemberVerifyReqDTO req) throws AuthorizationException {
        ResDTO<MemberVerifyResDTO> res = new ResDTO<>();
        res.setData(memberService.memberVerify(req));
        return res;
    }

    @GetMapping("member/sendVerifyEmail")
    @CheckLogin(isVerified = false)
    public @ResponseBody ResDTO<SendVerifyEmailResDTO> memberSendVerifyEmail(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<SendVerifyEmailResDTO> res = new ResDTO<>();
        res.setData(memberService.sendVerifyEmail(loginSource));
        return res;
    }

    @PostMapping("member/cidForget")
    public @ResponseBody ResDTO<MemberCidForgetResDTO> memberCidForget(
            @Valid @RequestBody MemberCidForgetReqDTO req) {
        ResDTO<MemberCidForgetResDTO> res = new ResDTO<>();
        res.setData(memberService.memberCidForget(req));
        return res;
    }
}
