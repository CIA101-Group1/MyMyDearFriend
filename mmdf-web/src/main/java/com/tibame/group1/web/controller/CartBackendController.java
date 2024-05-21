package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.web.CartReqDTO;
import com.tibame.group1.common.dto.web.CartResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class CartBackendController {

    @Autowired private CartService cartService;

    @PostMapping("cart/add")
    @CheckLogin
    public @ResponseBody ResDTO<String> addItemToCart(
            @Valid @RequestBody CartReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        return createResDTO(cartService.addProductToCart(req, loginSource));
    }

    @GetMapping("cart/get")
    @CheckLogin
    public @ResponseBody ResDTO<List<CartResDTO>> getCart(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        return createResDTO(cartService.getCartByMemberId(loginSource));
    }

    @DeleteMapping("cart/remove/{productId}")
    @CheckLogin
    public @ResponseBody ResDTO<String> removeItemFromCart(
            @PathVariable("productId") String productId,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        return createResDTO(cartService.removeProductFromCart(productId, loginSource));
    }

    @PutMapping("cart/update")
    @CheckLogin
    public @ResponseBody ResDTO<Integer> updateCart(
            @Valid @RequestBody CartReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
       return createResDTO(cartService.updateCart(req, loginSource));
    }

    private <T> ResDTO<T> createResDTO(T data) {
        ResDTO<T> res = new ResDTO<>();
        res.setData(data);
        return res;
    }
}
