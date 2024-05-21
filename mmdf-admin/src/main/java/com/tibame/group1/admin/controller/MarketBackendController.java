package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.dto.*;
import com.tibame.group1.admin.service.MarketService;
import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api")
public class MarketBackendController {

    @Autowired
    private MarketService marketService;

    @PostMapping("market/create")
    public @ResponseBody ResDTO<MarketCreateResDTO> marketCreate(
            @Valid @RequestBody MarketCreateReqDTO req,
            @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException,DateException, IOException {
        ResDTO<MarketCreateResDTO> res = new ResDTO<>();
        res.setData(marketService.marketCreate(req, adminLoginSource));
        return res;
    }

    @PostMapping("market/edit")
    public @ResponseBody ResDTO<MarketEditResDTO> marketEdit(
            @Valid @RequestBody MarketEditReqDTO req,
            @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException, DateException {
        ResDTO<MarketEditResDTO> res = new ResDTO<>();
        res.setData(marketService.marketEdit(req, adminLoginSource));
        return res;
    }

    @GetMapping("market/detail")
    public @ResponseBody ResDTO<List<MarketDetailResDTO>> marketDetail(
            @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource)
        throws  CheckRequestErrorException,IOException{
        ResDTO<List<MarketDetailResDTO>> res = new ResDTO<>();
        res.setData(marketService.marketDetail(adminLoginSource));
        return res;
    }
}
