package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.dto.OrderReqDTO;
import com.tibame.group1.admin.dto.OrderResDTO;
import com.tibame.group1.admin.dto.OrderUpdateReqDTO;
import com.tibame.group1.admin.service.OrderService;
import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.enums.OrderMemberIdentity;
import com.tibame.group1.common.enums.OrderStatus;
import com.tibame.group1.common.exception.CheckRequestErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class OrderBackendController {

    @Autowired private OrderService orderService;

    @GetMapping("order/getAll")
    public @ResponseBody ResDTO<List<OrderResDTO>> orderAll(
            @RequestParam(name = "orderStatus", required = false) OrderStatus orderStatus,
            @RequestParam(name = "orderId", required = false) String orderId,
            @RequestParam(name = "buyerName", required = false) String buyerName,
            @RequestParam(name = "sellerName", required = false) String sellerName)
            throws CheckRequestErrorException {
        OrderReqDTO req = new OrderReqDTO();
        req.setOrderStatus(orderStatus);
        req.setOrderId(orderId);
        req.setBuyerName(buyerName);
        req.setSellerName(sellerName);
        ResDTO<List<OrderResDTO>> res = new ResDTO<>();
        res.setData(orderService.orderGetAll(req));
        return res;
    }

    @PostMapping("order/update")
    public @ResponseBody ResDTO<String> orderUpdate(@RequestBody OrderUpdateReqDTO req)
            throws CheckRequestErrorException {
        ResDTO<String> res = new ResDTO<>();
        res.setData(orderService.orderUpdate(req));
        return res;
    }
}
