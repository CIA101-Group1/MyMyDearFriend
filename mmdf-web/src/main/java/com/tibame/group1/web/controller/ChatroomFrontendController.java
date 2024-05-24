package com.tibame.group1.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value="/chatroom")
public class ChatroomFrontendController {
    
//    @CheckLogin
    @GetMapping(value="test")
    public ModelAndView chatroom(Model model) {
        
        ModelAndView view = new ModelAndView();
        view.setViewName("/chatroom/chat-index");
        return view;
        
    }
    //========== 這裡之後要改取 membeId ========//
    //@CheckLogin
    @PostMapping(value="chat")
    public ModelAndView chat(@RequestParam("userName") String userName,Model model) {
        model.addAttribute("userName",userName);
        ModelAndView view = new ModelAndView();
        view.setViewName("/chatroom/testChat");
        return view;
    }

    @GetMapping(value = "service/helper")
    public ModelAndView helper(Model model) {
        ModelAndView view = new ModelAndView();
        view.setViewName("/chatroom/helper");
        return view;
    }
    @GetMapping(value = "/chat/test")
    public ModelAndView chatroomTest(Model model) {
        ModelAndView view = new ModelAndView();
        view.setViewName("/chatroom/chatroom-index");
        return view;

    }
}
