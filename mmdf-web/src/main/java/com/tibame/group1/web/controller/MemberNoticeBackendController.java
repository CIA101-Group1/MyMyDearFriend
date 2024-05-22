package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.web.MemberNoticeAllResDTO;
import com.tibame.group1.common.dto.web.MemberNoticeReqDTO;
import com.tibame.group1.common.dto.web.MemberNoticeUpdateResDTO;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.common.utils.NumberUtils;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.MemberNoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class MemberNoticeBackendController {

    @Autowired private MemberNoticeService memberNoticeService;

    @PostMapping("memberNotice/all")
    @CheckLogin(isVerified = false)
    public @ResponseBody ResDTO<MemberNoticeAllResDTO> memberNoticeAll(
            @RequestBody MemberNoticeReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource,
            @RequestParam(value = "page", defaultValue = "0") String pageNum,
            @RequestParam(value = "sizePerPage", defaultValue = "10") String sizePerPage)
            throws DateException {
        Sort sort = Sort.by(Sort.Direction.DESC, "sendingTime");
        Pageable pageable =
                PageRequest.of(NumberUtils.toInt(pageNum), NumberUtils.toInt(sizePerPage), sort);
        ResDTO<MemberNoticeAllResDTO> res = new ResDTO<>();
        res.setData(memberNoticeService.memberNoticeAll(req, loginSource, pageable));
        return res;
    }

    @GetMapping("memberNotice/updateReadingTime")
    @CheckLogin(isVerified = false)
    public @ResponseBody ResDTO<MemberNoticeUpdateResDTO> memberNoticeUpdateReadingTime(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<MemberNoticeUpdateResDTO> res = new ResDTO<>();
        res.setData(memberNoticeService.memberNoticeUpdateReadingTime(loginSource));
        return res;
    }
}
