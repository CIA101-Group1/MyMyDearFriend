package com.tibame.group1.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MarketFrontedController {

    @GetMapping("/market/all")
    public String marketAll(){return "/market/market-all";}

    @GetMapping("/market/create")
    public String marketCreate(){return "market/market-create";}

    @GetMapping("/market/detail")
    public String marketDetail() {
        return "market/market-detail";}

    @GetMapping("/market/{marketId}/registrations")
    public String marketRegistration(){
        return "market/market-registration";}

}
