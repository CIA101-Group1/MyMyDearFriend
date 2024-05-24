package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.dto.MarketResDTO;
import com.tibame.group1.web.service.MarketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MarketBackendController {
    @Autowired private MarketService marketService;

    @GetMapping("/market")
    public @ResponseBody ResDTO<List<MarketResDTO>> getMarketByStatus(){
        ResDTO<List<MarketResDTO>> res = new ResDTO<>();
        res.setData(marketService.getMarketByStatus());
        return res;
        }
}
