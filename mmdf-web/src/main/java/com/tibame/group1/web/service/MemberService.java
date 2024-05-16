package com.tibame.group1.web.service;

import com.tibame.group1.common.dto.web.*;
import com.tibame.group1.common.exception.AuthorizationException;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;
import com.tibame.group1.web.dto.LoginSourceDTO;

import java.io.IOException;

public interface MemberService {
    MemberCreateResDTO memberCreate(MemberCreateReqDTO req)
            throws DateException, IOException, CheckRequestErrorException;

    MemberDetailResDTO memberDetail(LoginSourceDTO loginSource);

    MemberEditResDTO memberEdit(MemberEditReqDTO req, LoginSourceDTO loginSource);

    LoginResDTO memberLogin(LoginReqDTO req) throws IOException;

    MemberVerifyResDTO memberVerify(MemberVerifyReqDTO req) throws AuthorizationException;

    SendVerifyEmailResDTO sendVerifyEmail(LoginSourceDTO loginSource);

    MemberCidForgetResDTO memberCidForget(MemberCidForgetReqDTO req);

    MemberCidResetResDTO memberCidReset(MemberCidResetReqDTO req) throws IOException;
}
