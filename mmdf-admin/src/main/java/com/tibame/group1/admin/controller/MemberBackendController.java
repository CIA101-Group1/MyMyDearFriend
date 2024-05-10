package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.dto.MemberAllReqDTO;
import com.tibame.group1.admin.dto.MemberResDTO;
import com.tibame.group1.admin.service.MemberService;
import com.tibame.group1.common.dto.ResDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class MemberBackendController {

    @Autowired private MemberService memberService;

    @PostMapping("member/all")
    public @ResponseBody ResDTO<MemberResDTO> memberAll(
            @RequestBody MemberAllReqDTO req,
            @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
            @RequestParam(value = "sizePerPage", defaultValue = "10") int sizePerPage) {
        Pageable pageable = PageRequest.of(pageNum, sizePerPage);
        ResDTO<MemberResDTO> res = new ResDTO<>();
        res.setData(memberService.memberAll(req, pageable));
        return res;
    }
}
