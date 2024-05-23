package com.tibame.group1.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BidProductPageController {
    @GetMapping("/bidproduct/list")
    public String GetBidproductListPage() {
        return "/bidproduct/bidproduct-list";
    }

    @GetMapping("/bidproduct/review")
    public String GetBidproductReviewPage() {
        return "/bidproduct/bidproduct-review";
    }
}
