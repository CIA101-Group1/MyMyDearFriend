package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.dto.MemberAllReqDTO;
import com.tibame.group1.admin.dto.MemberResDTO;
import com.tibame.group1.admin.service.MemberService;
import com.tibame.group1.common.dto.ResDTO;

import com.tibame.group1.common.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@CacheConfig(cacheNames = "member", keyGenerator = "mmdfKeyGenerator")
public class MemberBackendController {

    @Autowired private MemberService memberService;

    @PostMapping("member/all")
    @Cacheable
    public @ResponseBody ResDTO<MemberResDTO> memberAll(
            @RequestBody MemberAllReqDTO req,
            @RequestParam(value = "page", defaultValue = "0") String pageNum,
            @RequestParam(value = "sizePerPage", defaultValue = "10") String sizePerPage) {
        Pageable pageable =
                PageRequest.of(NumberUtils.toInt(pageNum), NumberUtils.toInt(sizePerPage));
        ResDTO<MemberResDTO> res = new ResDTO<>();
        res.setData(memberService.memberAll(req, pageable));
        return res;
    }
}
