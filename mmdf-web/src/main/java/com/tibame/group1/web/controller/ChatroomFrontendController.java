package com.tibame.group1.web.controller;

import com.tibame.group1.common.exception.AuthorizationException;
import com.tibame.group1.db.repository.ChatroomRepository;
import com.tibame.group1.web.annotation.CheckLogin;
import com.tibame.group1.web.dto.LoginSourceDTO;
import com.tibame.group1.web.service.ChatroomAddFriendService;
import com.tibame.group1.web.service.ChatroomService;
import com.tibame.group1.web.service.JwtService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value="/chatroom")
public class ChatroomFrontendController {
    @Autowired private ChatroomAddFriendService chatroomAddFriendService;
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
//    @CheckLogin
    @GetMapping(value = "/chat/test")
    public ModelAndView chatroomTest(Model model) {
        ModelAndView view = new ModelAndView();
        view.setViewName("/chatroom/chatroom-index");
        return view;

    }
    @PostMapping(value="/add-friend")
    public ModelAndView addFriend(@PathParam("authorization") String authorization,
            @PathParam("memberId")Integer memberId, Model model) throws AuthorizationException {
        chatroomAddFriendService.addFriend(authorization,memberId);
        ModelAndView view = new ModelAndView();
        view.setViewName("/chatroom/chatroom-index");
        return view;
    }
}
