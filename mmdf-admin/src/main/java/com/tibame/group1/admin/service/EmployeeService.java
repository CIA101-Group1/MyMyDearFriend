package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.*;
import com.tibame.group1.common.dto.web.LoginResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;

import java.io.IOException;

public interface EmployeeService {
    //員工創建也要驗證登入
    EmployeeCreateResDTO employeeCreate(EmployeeCreateReqDTO req, AdminLoginSourceDTO adminLoginSource) throws CheckRequestErrorException, IOException, DateException;

    EmployeeDetailResDTO employeeDetail(AdminLoginSourceDTO adminLoginSource) throws CheckRequestErrorException, IOException;

    EmployeeEditResDTO employeeEdit(EmployeeEditReqDTO req, AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException;

    LoginResDTO employeeLogin(AdminLoginReqDTO req) throws IOException;

    EmployeeResDTO employeeAll(EmployeeAllReqDTO req, AdminLoginSourceDTO adminLoginSource) throws CheckRequestErrorException, IOException,DateException;


}
