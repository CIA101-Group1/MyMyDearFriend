package com.tibame.group1.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value="/service")
public class ServiceFrontendCntroller {

    @GetMapping(value="/helper")
    public ModelAndView helper(Model model) {

        ModelAndView view = new ModelAndView();
        view.setViewName("/service-helper/helper-index");
        return view;

    }
}
