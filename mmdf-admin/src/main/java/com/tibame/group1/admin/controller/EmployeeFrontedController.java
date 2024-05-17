package com.tibame.group1.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class EmployeeFrontedController {

    @GetMapping("/employee/login")
    public String employeeLogin(Model model) {
        return "/employee/employee-login"; //要導入的html
    }

    @GetMapping("employee/detail")
    public String employeeDetail(Model model, @RequestParam("employeeAccount") String employeeAccount,
                                 @RequestParam("employeePassword") String employeePassword) {
        return "employee-detail";  //要導入的html
    }


}
