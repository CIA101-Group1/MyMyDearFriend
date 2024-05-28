package com.tibame.group1.admin.controller;

import com.tibame.group1.admin.dto.*;
import com.tibame.group1.admin.dto.AdminLoginReqDTO;
import com.tibame.group1.admin.service.EmployeeService;
import com.tibame.group1.common.dto.ResDTO;
import com.tibame.group1.common.dto.web.LoginResDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.common.exception.DateException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/")
public class EmployeeBackendController {

    @Autowired private EmployeeService employeeService;

    // 員工創建也要登入驗證
    @PostMapping("employee/create")
    public @ResponseBody ResDTO<EmployeeCreateResDTO> employeeCreate(
            @Valid @RequestBody EmployeeCreateReqDTO req,
            @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException, DateException {
        ResDTO<EmployeeCreateResDTO> res = new ResDTO<>();
        res.setData(employeeService.employeeCreate(req, adminLoginSource));
        return res;
    }

    @PostMapping("employee/edit")
    public @ResponseBody ResDTO<EmployeeEditResDTO> employeeEdit(
            @Valid @RequestBody EmployeeEditReqDTO req,
            @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException {
        ResDTO<EmployeeEditResDTO> res = new ResDTO<>();
        res.setData(employeeService.employeeEdit(req, adminLoginSource));
        return res;
    }

    @GetMapping("employee/detailOne")
    public @ResponseBody ResDTO<EmployeeDetailResDTO> employeeDetail(
            @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource)
            throws CheckRequestErrorException, IOException {
        ResDTO<EmployeeDetailResDTO> res = new ResDTO<>();
        res.setData(employeeService.employeeDetail(adminLoginSource));
        return res;
    }

    @GetMapping("employee/detail")
    public @ResponseBody ResDTO<EmployeeDetailResDTO> employeeDetailById(
            @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource,
            @RequestParam(value = "employeeId") Integer employeeId)
            throws CheckRequestErrorException {
        ResDTO<EmployeeDetailResDTO> res = new ResDTO<>();
        res.setData(employeeService.employeeDetailById(adminLoginSource, employeeId));
        return res;
    }

    @GetMapping("employee/all")
    public @ResponseBody ResDTO<List<EmployeeAllResDTO>> employeeAll(
            @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE) AdminLoginSourceDTO adminLoginSource,
            @RequestParam(value = "name", required = false) String employeeName)
            throws CheckRequestErrorException, IOException, DateException {
        ResDTO<List<EmployeeAllResDTO>> res = new ResDTO<>();
        res.setData(employeeService.employeeAll(adminLoginSource, employeeName));
        return res;
    }

    @PostMapping("employee/login")
    public @ResponseBody ResDTO<LoginResDTO> employeeLogin(@Valid @RequestBody AdminLoginReqDTO req)
            throws IOException {
        ResDTO<LoginResDTO> res = new ResDTO<>();
        res.setData(employeeService.employeeLogin(req));
        return res;
    }

    // 設定權限
    @PostMapping("employee/role")
    public @ResponseBody ResDTO<EmployeeRoleResDTO>assignRoleToEmployee(
            @Valid @RequestBody EmployeeRoleReqDTO employeeRoleReqDTO,
            @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE)
                    AdminLoginSourceDTO adminLoginSource) throws CheckRequestErrorException{
        ResDTO<EmployeeRoleResDTO> res = new ResDTO<>();
        res.setData(employeeService.assignRoleToEmployee(employeeRoleReqDTO,adminLoginSource));
        return res;
    }

    // 查詢權限
    @GetMapping("employee/allRole")
    public @ResponseBody ResDTO<EmployeeRoleDetailResDTO> employeeRole(
            @RequestAttribute(AdminLoginSourceDTO.ATTRIBUTE)
                    AdminLoginSourceDTO adminLoginSourceDTO){
        ResDTO<EmployeeRoleDetailResDTO> res = new ResDTO<>();
        res.setData(employeeService.employeeRole(adminLoginSourceDTO));
        return res;
    }
}
