package com.tibame.group1.frontend.interceptor;

import com.tibame.group1.common.exception.AuthorizationException;
import com.tibame.group1.common.utils.StringUtils;
import com.tibame.group1.db.entity.MemberEntity;
import com.tibame.group1.db.repository.MemberRepository;
import com.tibame.group1.frontend.ConfigProperties;
import com.tibame.group1.frontend.annotation.CheckLogin;
import com.tibame.group1.frontend.dto.LoginSourceDTO;
import com.tibame.group1.frontend.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

@Component
public class CheckLoginInterceptor implements HandlerInterceptor {

    @Autowired private JwtService jwtService;

    @Autowired private ConfigProperties config;

    @Autowired private MemberRepository memberRepository;

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler)
            throws AuthorizationException {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        Method method = handlerMethod.getMethod();
        CheckLogin checkLogin = method.getAnnotation(CheckLogin.class);
        if (null == checkLogin) {
            return true;
        }

        String authorization = request.getHeader("authorization");
        if (StringUtils.isEmpty(authorization)) {
            throw new AuthorizationException("登入驗證碼不可為空");
        }

        if (authorization.equals(config.getTestMemberAuthorization())) {
            MemberEntity member = memberRepository.findByMemberAccount(config.getMemberAccount());
            if (null != member) {
                LoginSourceDTO loginSource = new LoginSourceDTO();
                loginSource.setMemberId(member.getMemberId());
                loginSource.setName(member.getName());
                loginSource.setEmail(member.getEmail());
                loginSource.setIsVerified(member.getIsVerified());
                request.setAttribute(LoginSourceDTO.ATTRIBUTE, loginSource);
                return true;
            }
        }
        LoginSourceDTO loginSource = jwtService.decodeLogin(authorization);
        request.setAttribute(LoginSourceDTO.ATTRIBUTE, loginSource);
        return true;
    }
}
