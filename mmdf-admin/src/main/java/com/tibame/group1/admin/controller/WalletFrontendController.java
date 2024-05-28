package com.tibame.group1.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class WalletFrontendController {

    @GetMapping("wallets/getAll")
    public String getAllWallets() {
        return "wallet-request-history";
    }
}
