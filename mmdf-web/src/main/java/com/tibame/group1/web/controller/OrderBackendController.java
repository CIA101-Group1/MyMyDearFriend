package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.web.*;
import com.tibame.group1.db.entity.OrderDetailEntity;
import com.tibame.group1.db.entity.OrderEntity;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.dto.OrderCreateReqDTO;
import com.tibame.group1.web.dto.OrderCreateResDTO;
import com.tibame.group1.web.service.OrderService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class OrderBackendController {

    @Autowired private OrderService orderService;

    @PostMapping("order/create")
    @CheckLogin
    public @ResponseBody ResDTO<OrderCreateResDTO> orderCreate(
            @Valid @RequestBody OrderCreateReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<OrderCreateResDTO> res = new ResDTO<>();
        res.setData(orderService.orderCreate(req, loginSource));
        return res;
    }

    @GetMapping("order/sellerGetAll")
    @CheckLogin
    public @ResponseBody ResDTO<List<OrderEntity>> orderSellerGetAll(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<List<OrderEntity>> res = new ResDTO<>();
        res.setData(orderService.orderSellerGetAll(loginSource));
        return res;
    }

    @GetMapping("order/buyerGetAll")
    @CheckLogin
    public @ResponseBody ResDTO<List<OrderEntity>> orderBuyerGetAll(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<List<OrderEntity>> res = new ResDTO<>();
        res.setData(orderService.orderBuyerGetAll(loginSource));
        return res;
    }

    @GetMapping("order/detail/{orderId}")
    public @ResponseBody ResDTO<List<OrderDetailEntity>> orderDetail(
            @PathVariable("orderId") Integer orderId) {
        ResDTO<List<OrderDetailEntity>> res = new ResDTO<>();
        res.setData(orderService.orderDetail(orderId));
        return res;
    }
}
