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
        log.error("無法查詢員工詳細資訊");
        return "/employee/employee-detail";  //要導入的html
    }

    @GetMapping("/employee/all")
    public String index() {
        log.error("無法查詢所有員工資料");
        return "/employee/employee-all";
    }
}



