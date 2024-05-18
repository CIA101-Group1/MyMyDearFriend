package com.tibame.group1.web.service.impl;

import com.tibame.group1.common.dto.web.CartReqDTO;
import com.tibame.group1.common.dto.web.CartResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.dao.CartDao;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.CartService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CartServiceImpl implements CartService {

    @Autowired CartDao cartDao;

    @Autowired private MemberRepository memberRepository;

    @Override
    public String addProductToCart(CartReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        // 調用getValidatedMember檢查會員是否存在
        MemberEntity member = getValidatedMember(loginSource);
        req.setMemberId(member.getMemberId().toString());
        return cartDao.addProductToCart(req);
    }

    @Override
    public List<CartResDTO> getCartByMemberId(LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        // 調用getValidatedMember檢查會員是否存在
        MemberEntity member = getValidatedMember(loginSource);
        return cartDao.getCartByMemberId(member.getMemberId().toString());
    }

    @Override
    public String removeProductFromCart(String productId, LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        // 調用getValidatedMember檢查會員是否存在
        MemberEntity member = getValidatedMember(loginSource);
        return cartDao.removeProductFromCart(productId, member.getMemberId().toString());
    }

    @Override
    public String updateCart(CartReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        // 調用getValidatedMember檢查會員是否存在
        MemberEntity member = getValidatedMember(loginSource);
        req.setMemberId(member.getMemberId().toString());
        return cartDao.updateCart(req);
    }

    // 驗證會員是否存在
    private MemberEntity getValidatedMember(LoginSourceDTO loginSource) throws CheckRequestErrorException {
        MemberEntity member = memberRepository.findById(loginSource.getMemberId()).orElse(null);
        if (member == null) {
            log.warn("查無會員資料 {}", loginSource.getMemberId());
            throw new CheckRequestErrorException("查無會員資料");
        }
        return member;
    }
}
