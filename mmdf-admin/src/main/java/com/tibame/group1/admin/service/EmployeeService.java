package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.*;
import com.tibame.group1.common.dto.web.LoginResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;


import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    // 員工創建也要驗證登入
    EmployeeCreateResDTO employeeCreate(
            EmployeeCreateReqDTO req, AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException, DateException;

    /**
     * 登入員工看自己資訊
     *
     * @param adminLoginSource
     * @return
     * @throws CheckRequestErrorException
     * @throws IOException
     */
    EmployeeDetailResDTO employeeDetail(AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException;

    EmployeeDetailResDTO employeeDetailById(AdminLoginSourceDTO adminLoginSource, Integer employeeId)
        throws CheckRequestErrorException;


    EmployeeEditResDTO employeeEdit(EmployeeEditReqDTO req, AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException;

    LoginResDTO employeeLogin(AdminLoginReqDTO req) throws IOException;

    List<EmployeeAllResDTO> employeeAll(AdminLoginSourceDTO adminLoginSource, String employeeName)
            throws CheckRequestErrorException, IOException, DateException;

    //設定員工權限
    EmployeeRoleResDTO assignRoleToEmployee(EmployeeRoleReqDTO employeeRoleReq, AdminLoginSourceDTO adminLoginSource) throws CheckRequestErrorException;

    //根據員工id查詢員工權限
    EmployeeRoleDetailResDTO employeeRole(AdminLoginSourceDTO adminLoginSource, Integer employeeId);
}
