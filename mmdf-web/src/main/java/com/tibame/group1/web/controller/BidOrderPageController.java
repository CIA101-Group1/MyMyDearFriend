package com.tibame.group1.web.controller;

import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.BidOrderEntity;
import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.web.service.BidOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BidOrderPageController {
    @Autowired
    BidOrderService bidOrderService;

    @GetMapping("/seller/bidorder")
    public String getSellerBidOrderPage() {
        return "/bidorder/seller-bidorder-list";
    }

    @GetMapping("/buyer/bidorder")
    public String getBuyerBidOrderPage() {
        return "/bidorder/buyer-bidorder-list";
    }

    @GetMapping("/bidorder/checkout/{orderId}")
    public String getBidOrderCheckoutPage(@PathVariable("orderId") Integer orderId, Model model) throws CheckRequestErrorException {
        BidOrderEntity bidOrder = bidOrderService.findById(orderId);
        model.addAttribute("BidOrder", bidOrder);
        return "/bidorder/bidorder-checkout";
    }
}
