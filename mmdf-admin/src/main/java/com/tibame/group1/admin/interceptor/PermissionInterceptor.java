package com.tibame.group1.admin.interceptor;

import com.tibame.group1.admin.dto.AdminLoginSourceDTO;
import com.tibame.group1.admin.service.JwtService;
import com.tibame.group1.db.repository.EmployeeRoleRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired private EmployeeRoleRepository employeeRoleRepository;

    @Autowired private JwtService jwtService;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 从请求属性中获取登录信息
        AdminLoginSourceDTO adminLoginSource =
                (AdminLoginSourceDTO) request.getAttribute(AdminLoginSourceDTO.ATTRIBUTE);

        // 获取员工ID
        int employeeId = adminLoginSource.getEmployeeId();

        // 根据员工ID查询角色ID
        Integer roleIds = employeeRoleRepository.findRoleIdsByEmployeeId(employeeId);

        // 进行权限检查
        if (!checkPermissions(roleIds, request.getRequestURI())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        // 权限通过，放行请求
        return true;
    }

        private boolean checkPermissions(Integer roleId, String requestURI) {
            // 在这里根据用户角色ID和请求的 URI 进行权限检查
            // 如果用户有权限访问请求的 URI，返回 true，否则返回 false

            // 假设角色ID为1的用户是超级管理员，拥有所有权限
            if (roleId.equals(1)) {
                return true;
            }

            // 其他角色ID的用户根据不同的角色来确定权限
            switch (roleId) {
                case 3:
                    // 假设角色ID为2的用户是活动管理员，只有访问市场活动页面的权限
                    return requestURI.contains("/market/");
                case 2:
                    // 假设角色ID为3的用户是客服管理员，只有访问客服管理页面的权限
                    return requestURI.contains("/service");
                default:
                    // 其他情况，默认没有权限
                    return false;
            }
        }
    }
