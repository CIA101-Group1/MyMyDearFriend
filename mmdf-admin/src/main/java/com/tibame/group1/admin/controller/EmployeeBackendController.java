package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.dto.*;
import com.tibame.group1.admin.service.EmployeeService;
import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.admin.dto.AdminLoginReqDTO;
import com.tibame.group1.common.dto.web.LoginResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class EmployeeBackendController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("employee/create")
    public @ResponseBody ResDTO<EmployeeCreateResDTO> employeeCreate(
            @Valid @RequestBody EmployeeCreateReqDTO req)
            throws DateException, IOException {
        ResDTO<EmployeeCreateResDTO> res = new ResDTO<>();
        res.setData(employeeService.employeeCreate(req));
        return res;
    }

    @PostMapping("employee/edit")
    public ResDTO<?> employeeEdit(
            @Valid @RequestBody EmployeeEditReqDTO req,
            @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException {
        employeeService.employeeEdit(req, adminLoginSource);
        return new ResDTO<>();
    }

    @GetMapping("employee/detail")
    public @ResponseBody ResDTO<EmployeeDetailResDTO> employeeDetail(
            @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException {
        ResDTO<EmployeeDetailResDTO> res = new ResDTO<>();
        res.setData(employeeService.employeeDetail(adminLoginSource));
        return res;
    }

    @PostMapping("employee/login")
    public @ResponseBody ResDTO<LoginResDTO> employeeLogin(@Valid @RequestBody AdminLoginReqDTO req)
            throws IOException {
        ResDTO<LoginResDTO> res = new ResDTO<>();
        res.setData(employeeService.employeeLogin(req));
        return res;
    }


}
