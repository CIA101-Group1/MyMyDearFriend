package com.tibame.group1.admin.interceptor;


import com.tibame.group1.common.exception.AuthorizationException;
import com.tibame.group1.db.repository.EmployeeRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private EmployeeRoleRepository employeeRoleRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在這裡進行權限檢查
        // 根據請求中的員工id，查找對應的角色ID
        // 然後根據角色ID檢查是否有權限訪問該url

        // 從請求中獲取員工ID
        Integer employeeId = Integer.parseInt(request.getHeader("employeeId"));
        try {
            // 根據員工id查詢相關角色id
            List<Integer> roleIds = employeeRoleRepository.findRoleIdsByEmployeeId(employeeId);

            // 检查角色ID中是否包含超级管理員、活动管理員、客服管理員等特定角色
            for (Integer roleId : roleIds) {
                if (roleId.equals(1)) {
                    // 如果是超级管理員的角色ID，放行
                    return true;
                } else if (roleId.equals(2) && request.getRequestURI().contains("/activity")) {
                    // 如果是活动管理員的角色ID，並且訪問市集活動頁面放行
                    return true;
                } else if (roleId.equals(3) && request.getRequestURI().contains("/service")) {
                    // 如果是客服管理員的角色ID，並且訪問的是客服管理頁面，放行
                    return true;
                }
            }

            // 如果没有任何一个角色符合要求，拒绝访问
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        } catch (NumberFormatException e) {
            //當無法解析員工ID時拋出異常
            throw new AuthorizationException("無效的員工ID");
        } catch (Exception e) {
            //其他異常情況下拋出通用的權限檢查異常
            throw new AuthorizationException("權限檢查失敗");
        }
    }
}

