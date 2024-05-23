package com.tibame.group1.web.controller;

import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.FavoriteProductReqDTO;
import com.tibame.group1.web.dto.FavoriteProductResDTO;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.FavoriteProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class FavoriteProductBackendController {

    @Autowired
    private FavoriteProductService favoriteProductService;

    @PostMapping("favorite/add")
    @CheckLogin
    public ResDTO<String> addProductTOFavorite(
            @RequestBody FavoriteProductReqDTO req,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        return createResDTO(favoriteProductService.addProductToFavorite(req, loginSource));
    }

    @GetMapping("favorite/get")
    @CheckLogin
    public @ResponseBody ResDTO<List<FavoriteProductResDTO>> getFavoriteProduct(
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        return createResDTO(favoriteProductService.getProductToFavorite(loginSource));
    }

    @DeleteMapping("favorite/remove/{productId}")
    @CheckLogin
    public @ResponseBody ResDTO<String> removeItemFromCart(
            @PathVariable("productId") Integer productId,
            @RequestAttribute(LoginSourceDTO.ATTRIBUTE) LoginSourceDTO loginSource)
            throws CheckRequestErrorException {
        return createResDTO(favoriteProductService.removeProductFromFavorite(loginSource, productId));
    }

    private <T> ResDTO<T> createResDTO(T data) {
        ResDTO<T> res = new ResDTO<>();
        res.setData(data);
        return res;
    }
}
