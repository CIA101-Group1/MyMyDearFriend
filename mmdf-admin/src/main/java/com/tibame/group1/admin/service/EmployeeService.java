package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.*;
import com.tibame.group1.admin.dto.LoginReqDTO;
import com.tibame.group1.common.dto.web.LoginResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;

import java.io.IOException;

public interface EmployeeService {

    EmployeeCreateResDTO employeeCreate(EmployeeCreateReqDTO req) throws DateException, IOException;

    EmployeeDetailResDTO employeeDetail(LoginSourceDTO loginSource) throws CheckRequestErrorException;

    void employeeEdit(EmployeeEditReqDTO req, LoginSourceDTO loginSource)
            throws CheckRequestErrorException, IOException;

    LoginResDTO employeeLogin(LoginReqDTO req) throws IOException;


}
