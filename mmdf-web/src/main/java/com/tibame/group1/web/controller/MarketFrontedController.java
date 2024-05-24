package com.tibame.group1.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MarketFrontedController {

    @GetMapping("/market")
    public String marketAll(){return "market/market";}
}
