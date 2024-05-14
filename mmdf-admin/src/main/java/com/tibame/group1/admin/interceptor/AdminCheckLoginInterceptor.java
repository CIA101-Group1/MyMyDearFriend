package com.tibame.group1.admin.interceptor;

import com.tibame.group1.admin.dto.AdminLoginSourceDTO;
import com.tibame.group1.admin.service.JwtService;
import com.tibame.group1.common.exception.AuthorizationException;
import com.tibame.group1.common.utils.StringUtils;
import com.tibame.group1.db.repository.EmployeeRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminCheckLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmployeeRoleRepository employeeRoleRepository;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws AuthorizationException {
        String authorization = request.getHeader("authorization");
        if (StringUtils.isEmpty(authorization)) {
            throw new AuthorizationException("登入驗證碼不可為空");
        }

        //檢查登入驗證
        AdminLoginSourceDTO loginSource = jwtService.decodeLogin(authorization);
        request.setAttribute(AdminLoginSourceDTO.ATTRIBUTE, loginSource);
        return true;
    }


}
