package com.tibame.group1.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class EmployeeFrontedController {

    @GetMapping("/employee/login")
    public String employeeLogin() {
        return "/employee/employee-login"; //要導入的html
    }

    @GetMapping("/employee/detail")
    public String employeeDetail() {
        return "employee/employee-detail";  //要導入的html
    }

    @GetMapping("/employee/all")
    public String index() {
        return "/employee/employee-all";
    }

    @GetMapping("/employee/create")
    public String employeeCreate() {
        return "employee/employee-create";
    }

    @GetMapping("/employee/detailOne")
    public String employeeDetailOne() {
        return "employee/employee-detail-one";
    }
}



