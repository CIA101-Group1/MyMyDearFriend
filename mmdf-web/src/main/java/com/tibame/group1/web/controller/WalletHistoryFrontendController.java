package com.tibame.group1.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WalletHistoryFrontendController {

    @GetMapping("member/walletHistory")
    public String walletHistory() {
        return "/member/member-wallet-history";
    }

    @GetMapping("member/walletAdd")
    public String walletAdd() {
        return "/member/member-wallet-add";
    }

    @GetMapping("member/walletWithdraw")
    public String walletWithdraw() {
        return "/member/member-wallet-withdraw";
    }

    @GetMapping("member/walletWithdrawHistory")
    public String walletWithdrawHistory() {
        return "/member/member-wallet-withdraw-history";
    }
}
