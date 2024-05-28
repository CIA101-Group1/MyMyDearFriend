package com.tibame.group1.admin.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/chatroom")
public class ServiceChatroomController {

    @GetMapping(value = "/service")
    public ModelAndView serviceChatroom(Model model) {
        ModelAndView view = new ModelAndView();
        view.setViewName("/chatroom/servicechatroom-index");
        return view ;
    }

    @GetMapping(value = "/history")
    public ModelAndView serviceHistory(Model model) {
        ModelAndView view = new ModelAndView();
        view.setViewName("/chatroom/servicechatroom-history");
        return view ;
    }
}
