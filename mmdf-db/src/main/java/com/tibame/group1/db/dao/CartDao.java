package com.tibame.group1.db.dao;

import com.tibame.group1.common.dto.web.CartReqDTO;
import com.tibame.group1.common.dto.web.CartResDTO;

import java.util.List;

public interface CartDao {

    String addProductToCart(CartReqDTO req);

    List<CartResDTO> getCartByMemberId(String memberId);

    String removeProductFromCart(String productId, String memberId);

    Integer updateCart(CartReqDTO req);
}
