package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.web.dto.OrderCreateReqDTO;
import com.tibame.group1.web.dto.OrderCreateResDTO;
import com.tibame.group1.web.service.OrderService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mmdf/web/api/")
public class OrderBackendController {

    @Autowired
    private OrderService orderService;

    @PostMapping("order/create")
    public @ResponseBody ResDTO<OrderCreateResDTO> orderCreate(
            @Valid @RequestBody OrderCreateReqDTO req) {
        ResDTO<OrderCreateResDTO> res = new ResDTO<>();
        res.setData(orderService.orderCreate(req));
        return res;
    }
}
