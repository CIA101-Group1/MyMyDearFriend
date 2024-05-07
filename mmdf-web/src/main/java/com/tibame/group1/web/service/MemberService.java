package com.tibame.group1.web.service;

import com.tibame.group1.common.dto.web.*;
import com.tibame.group1.common.exception.AuthorizationException;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.web.dto.LoginSourceDTO;

import java.io.IOException;

public interface MemberService {
    MemberCreateResDTO memberCreate(MemberCreateReqDTO req) throws DateException, IOException, CheckRequestErrorException;

    MemberDetailResDTO memberDetail(LoginSourceDTO loginSource) throws CheckRequestErrorException;

    void memberEdit(MemberEditReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException, IOException;

    LoginResDTO memberLogin(LoginReqDTO req) throws IOException;

    MemberVerifyResDTO memberVerify(MemberVerifyReqDTO req) throws AuthorizationException;

    void sendVerifyEmail(LoginSourceDTO loginSource) throws CheckRequestErrorException;
}
