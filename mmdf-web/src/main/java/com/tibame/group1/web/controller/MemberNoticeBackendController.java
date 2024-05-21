package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.web.MemberNoticeAllResDTO;
import com.tibame.group1.common.dto.web.MemberNoticeReqDTO;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.common.utils.NumberUtils;
import com.tibame.group1.web.service.MemberNoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class MemberNoticeBackendController {

    @Autowired private MemberNoticeService memberNoticeService;

    @PostMapping("memberNotice/all")
    public @ResponseBody ResDTO<MemberNoticeAllResDTO> memberNoticeAll(
            @RequestBody MemberNoticeReqDTO req,
            @RequestParam(value = "page", defaultValue = "0") String pageNum,
            @RequestParam(value = "sizePerPage", defaultValue = "10") String sizePerPage)
            throws DateException {
        Pageable pageable =
                PageRequest.of(NumberUtils.toInt(pageNum), NumberUtils.toInt(sizePerPage));
        ResDTO<MemberNoticeAllResDTO> res = new ResDTO<>();
        res.setData(memberNoticeService.memberNoticeAll(req, pageable));
        return res;
    }
}
