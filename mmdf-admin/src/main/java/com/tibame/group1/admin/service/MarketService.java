package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.*;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;


import java.io.IOException;
import java.util.List;

public interface MarketService {

    MarketCreateResDTO marketCreate(MarketCreateReqDTO req, AdminLoginSourceDTO adminLoginSource) throws CheckRequestErrorException, DateException, IOException;

    List<MarketDetailResDTO> marketDetail(AdminLoginSourceDTO adminLoginSource) throws CheckRequestErrorException;

    MarketEditResDTO marketEdit(MarketEditReqDTO req, AdminLoginSourceDTO adminLoginSource)
        throws CheckRequestErrorException,IOException, DateException;


}
