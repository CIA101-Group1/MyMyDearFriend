package com.tibame.group1.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class ChatroomBackendController {
    @PostMapping("member/holle")
    public @ResponseBody String creatRoom(Model model){
        String memberId = model.getAttribute("userName").toString();
        model.addAttribute("memberId", memberId);
        return null;
    }
}
