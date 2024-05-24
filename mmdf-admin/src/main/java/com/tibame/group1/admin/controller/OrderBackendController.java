package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.dto.AdminLoginSourceDTO;
import com.tibame.group1.admin.dto.OrderResDTO;
import com.tibame.group1.admin.service.OrderService;
import com.tibame.group1.common.dto.ResDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class OrderBackendController {

    @Autowired private OrderService orderService;

    @GetMapping("order/getAll")
    public @ResponseBody ResDTO<List<OrderResDTO>> orderAll(
            @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource) {
        ResDTO<List<OrderResDTO>> res = new ResDTO<>();
        res.setData(orderService.orderGetAll());
        return res;
    }
}
