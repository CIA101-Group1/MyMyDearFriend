package com.tibame.group1.web.controller;

import com.tibame.group1.db.entity.BidProductEntity;
import com.tibame.group1.web.service.impl.BidProductServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class BidProductPageController {

    @Autowired BidProductServiceImpl bidProductService;

    @GetMapping("/bidproduct")
    public String getBidProductShopPage() {
        return "/bidproduct/bidproduct-shop";
    }

    @GetMapping("/bidproduct-detail/{productId}")
    public String getBidProductDetailPage(
            @PathVariable(name = "productId") Integer productId, Model model) {
        BidProductEntity bidProductEntity = bidProductService.findById(productId);
        List<String> base64Images =
                bidProductEntity.getImages().stream()
                        .map(
                                imageEntity ->
                                        java.util.Base64.getEncoder()
                                                .encodeToString(imageEntity.getImage()))
                        .collect(Collectors.toList());
        model.addAttribute("bidProductEntity", bidProductEntity);
        model.addAttribute("images", base64Images);
        return "/bidproduct/bidproduct-detail";
    }

    @GetMapping("/seller/bidproduct")
    public String getSellerBidProductPage() {
        return "/bidproduct/seller-bidproduct-list";
    }

    @GetMapping("/seller/bidproduct-add")
    public String getSellerBidProductAddPage() {
        return "/bidproduct/seller-bidproduct-add";
    }

    @GetMapping("/seller/bidproduct-edit/{productId}")
    public String getSellerBidProductEditPage(@PathVariable(name = "productId") Integer productId, Model model) {
        model.addAttribute("productId", productId);
        return "/bidproduct/seller-bidproduct-edit";
    }

    @GetMapping("/bidproduct/checkout")
    public String getCheckoutPage() {
        return "/bidproduct/bidproduct-checkout";
    }

    @GetMapping("/buyer/bidproduct/record")
    public String getBuyerBidProductRecordPage() {
        return "/bidproduct/buyer-bidproduct-record";
    }
}
