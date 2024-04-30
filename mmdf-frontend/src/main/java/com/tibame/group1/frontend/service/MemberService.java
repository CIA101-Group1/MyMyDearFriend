package com.tibame.group1.frontend.service;

import com.tibame.group1.common.dto.frontend.*;
import com.tibame.group1.common.exception.AuthorizationException;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.frontend.dto.LoginSourceDTO;

import java.io.IOException;

public interface MemberService {
    MemberCreateResDTO memberCreate(MemberCreateReqDTO req) throws DateException, IOException;

    MemberDetailResDTO memberDetail(LoginSourceDTO loginSource) throws CheckRequestErrorException;

    void memberEdit(MemberEditReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException, IOException;

    LoginResDTO memberLogin(LoginReqDTO req) throws IOException;

    MemberVerifyResDTO memberVerify(MemberVerifyReqDTO req) throws AuthorizationException;

    void sendVerifyEmail(LoginSourceDTO loginSource) throws CheckRequestErrorException;
}
