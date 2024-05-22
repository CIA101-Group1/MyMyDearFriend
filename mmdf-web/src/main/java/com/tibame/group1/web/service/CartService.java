package com.tibame.group1.web.service;

import com.tibame.group1.common.dto.web.CartReqDTO;
import com.tibame.group1.common.dto.web.CartResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.web.dto.LoginSourceDTO;

import java.util.List;

public interface CartService {

    String addProductToCart(CartReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException;

    List<CartResDTO> getCartByMemberId(LoginSourceDTO loginSource)
            throws CheckRequestErrorException;

    String removeProductFromCart(String productId, LoginSourceDTO loginSource)
            throws CheckRequestErrorException;

    Integer updateCart(CartReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException;
}
