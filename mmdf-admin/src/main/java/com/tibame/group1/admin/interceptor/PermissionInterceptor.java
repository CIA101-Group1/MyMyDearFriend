package com.tibame.group1.admin.interceptor;

import com.tibame.group1.admin.dto.AdminLoginSourceDTO;
import com.tibame.group1.db.repository.EmployeeRoleRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired private EmployeeRoleRepository employeeRoleRepository;

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 從請求中獲取登入訊息
        AdminLoginSourceDTO adminLoginSource =
                (AdminLoginSourceDTO) request.getAttribute(AdminLoginSourceDTO.ATTRIBUTE);

        // 獲取員工ID
        int employeeId = adminLoginSource.getEmployeeId();

        // 根據員工ID查詢角色ID
        Integer roleIds = employeeRoleRepository.findRoleIdsByEmployeeId(employeeId);

        // 進行權限檢查，false轉傳到權限不足畫面
        if (!checkPermissions(roleIds, request.getRequestURI())) {
            response.sendRedirect(request.getContextPath() + "/permission");
            return false;
        }

        // 權限通過，放行請求
        return true;
    }

    private boolean checkPermissions(Integer roleId, String requestURI) {
        // 根據角色ID和請求的URI進行權限檢查
        // 如果員工有權限訪問請求的 URI，返回 true，否則返回 false

        // 假設角色ID為1的員工是超級管理員，擁有所有權限
        if (roleId.equals(1)) {
            return true;
        }

        // 其他角色ID的員工根據不同的角色來確定權限
        switch (roleId) {
            case 3:
                // 假設角色ID為3的員工是活動管理員，只有訪問活動頁面的權限
                return requestURI.contains("/market/");
            case 2:
                // 假设角色ID為2的員工是客服管理員，只有訪問客服管理頁面的權限
                return requestURI.contains("/chatroom/");
            default:
                // 其他情況，默認沒有權限
                return false;
        }
    }
}
