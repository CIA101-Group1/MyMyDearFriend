package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.enums.OrderMemberStatus;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.OrderEntity;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.*;
import com.tibame.group1.web.service.OrderService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tibame.group1.common.enums.OrderMemberStatus.*;

@RestController
@RequestMapping("api/")
public class OrderBackendController {

    @Autowired private OrderService orderService;

    @PostMapping("order/create")
    @CheckLogin
    public @ResponseBody ResDTO<OrderCreateResDTO> orderCreate(
            @Valid @RequestBody OrderCreateReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        ResDTO<OrderCreateResDTO> res = new ResDTO<>();
        res.setData(orderService.orderCreate(req, loginSource));
        return res;
    }

    @GetMapping("order/{orderId}/{status}")
    @CheckLogin
    public @ResponseBody ResDTO<OrderResDTO> orderGetById(
            @PathVariable("orderId") Integer orderId,
            @RequestParam(defaultValue = "NONE") OrderMemberStatus status,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource){
        ResDTO<OrderResDTO> res = new ResDTO<>();
        res.setData(orderService.orderBuyerGetById(orderId, loginSource, status));
        return res;
    }

    @GetMapping("order/{orderId}/detail")
    @CheckLogin
    public @ResponseBody ResDTO<List<OrderDetailResDTO>> orderDetail(
            @PathVariable("orderId") Integer orderId,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<List<OrderDetailResDTO>> res = new ResDTO<>();
        res.setData(orderService.orderDetail(orderId, loginSource));
        return res;
    }

    @GetMapping("order/GetAll")
    @CheckLogin
    public @ResponseBody ResDTO<List<OrderResDTO>> orderGetAll(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource,
            @RequestParam(name = "status", defaultValue = "NONE") OrderMemberStatus status) {
        ResDTO<List<OrderResDTO>> res = new ResDTO<>();
        res.setData(orderService.orderGetAll(loginSource, status));
        return res;
    }

    @PostMapping("order/update")
    @CheckLogin
    public @ResponseBody ResDTO<OrderUpdateResDTO> orderUpdate(
            @Valid @RequestBody OrderUpdateReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource) {
        ResDTO<OrderUpdateResDTO> res = new ResDTO<>();
        res.setData(orderService.orderUpdate(req, loginSource));
        return res;
    }
}
