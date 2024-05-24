package com.tibame.group1.web.service;

import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.web.dto.FavoriteProductReqDTO;
import com.tibame.group1.web.dto.FavoriteProductResDTO;
import com.tibame.group1.web.dto.LoginSourceDTO;

import java.util.List;

public interface FavoriteProductService {
    String addProductToFavorite(FavoriteProductReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException;

    List<FavoriteProductResDTO> getProductToFavorite(LoginSourceDTO loginSource)
            throws CheckRequestErrorException;

    String removeProductFromFavorite(LoginSourceDTO loginSource, Integer productId)
            throws CheckRequestErrorException;
}
