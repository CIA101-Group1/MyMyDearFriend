package com.tibame.group1.db.dao.impl;

import com.tibame.group1.common.dto.web.CartReqDTO;
import com.tibame.group1.common.dto.web.CartResDTO;
import com.tibame.group1.db.dao.CartDao;

import com.tibame.group1.db.entity.ProductEntity;
import com.tibame.group1.db.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CartDaoImpl implements CartDao {

    private static final String ADD_TO_CART_MESSAGE = "add to cart";
    private static final String REMOVE_FROM_CART_MESSAGE = "removed from cart";
    private static final String UPDATE_CART_MESSAGE = "update cart";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    @Qualifier("redisTemplateCart")
    private RedisTemplate<String, Object> redisTemplateCart;

    @Override
    public String addProductToCart(CartReqDTO req) {
        // 新增商品到購物車
        redisTemplateCart.opsForHash().put(req.getMemberId(), req.getProductId(), req.getQuantity());
        return ADD_TO_CART_MESSAGE;
    }

    @Override
    public List<CartResDTO> getCartByMemberId(String memberId) {
        // 查詢購物車所有商品、數量
        Map<Object, Object> map = redisTemplateCart.opsForHash().entries(memberId);
        // 將查詢資料轉換至CartResDTO
        List<CartResDTO> cartList = new ArrayList<>();
        for(Object key : map.keySet()){
            ProductEntity product = productRepository.findById(Integer.parseInt(key.toString())).orElse(null);
            CartResDTO cart = new CartResDTO();
            cart.setMemberId(memberId);
            cart.setProductId(key.toString());
            cart.setProductName(product.getName());
            cart.setPrice(product.getPrice());
            cart.setQuantity((Integer) map.get(key));
            cart.setSubtotal(product.getPrice() * (Integer) map.get(key));
            cartList.add(cart);
        }
        return cartList;
    }

    @Override
    public String removeProductFromCart(String productId, String memberId) {
        // 移除購物車商品
        redisTemplateCart.opsForHash().delete(memberId, productId);
        return REMOVE_FROM_CART_MESSAGE;
    }

    @Override
    public String updateCart(CartReqDTO req) {
        // 更新購物車商品
        redisTemplateCart.opsForHash().put(req.getMemberId(), req.getProductId(), req.getQuantity());
        return UPDATE_CART_MESSAGE;
    }
}
