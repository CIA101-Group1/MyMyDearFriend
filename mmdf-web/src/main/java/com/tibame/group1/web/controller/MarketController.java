package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.dto.MarketResDTO;
import com.tibame.group1.web.service.MarketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class MarketController {
    @Autowired private MarketService marketService;

    @GetMapping("")
    public @ResponseBody ResDTO<List<MarketResDTO>> getMarketByStatus(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<List<MarketResDTO>> res = new ResDTO<>();
        res.setData(marketService.getMarketByStatus(loginSource));
        return res;
        }
}
