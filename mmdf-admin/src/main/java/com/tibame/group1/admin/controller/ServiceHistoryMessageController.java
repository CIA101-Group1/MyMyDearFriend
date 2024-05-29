package com.tibame.group1.admin.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tibame.group1.admin.annotation.CheckLogin;
import com.tibame.group1.admin.dto.ServiceHistoryReqDTO;
import com.tibame.group1.admin.dto.ServiceMessageDTO;
import com.tibame.group1.admin.service.ChatroomHistoryService;
import com.tibame.group1.common.dto.ResDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/")
public class ServiceHistoryMessageController {
    @Autowired private ChatroomHistoryService chatroomHistoryService;

    @PostMapping("chatroom/history")
    public ResDTO<List<ServiceMessageDTO>> serviceHistory(@RequestBody ServiceHistoryReqDTO reqDTO) {
        System.out.println("進入 serviceHistory");
        ResDTO<List<ServiceMessageDTO>> resDTO = new ResDTO<>();
        try {
            List<ServiceMessageDTO> snedList = chatroomHistoryService.getHistoryMessage(reqDTO);
            resDTO.setMessage("成功");
            resDTO.setData(snedList);
        } catch (Exception e) {
            e.printStackTrace();
            resDTO.setMessage("失敗: " + e.getMessage());
        }
        System.out.println("送出 serviceHistory");
        return resDTO;
    }
//    @PostMapping("chatroom/history")
//    public List<ServiceMessageDTO> serviceHistory(
//            @RequestBody ServiceHistoryReqDTO reqDTO) {
//        System.out.println("進入");
//
////        ResDTO resDTO = new ResDTO();
////        resDTO.setData(chatroomHistoryService.getHistoryMessage(reqDTO));
//        List<ServiceMessageDTO> snedList = chatroomHistoryService.getHistoryMessage(reqDTO);
//        System.out.println("送出");
//        return snedList;
//    }
}
