package com.tibame.group1.admin.service.impl;

import com.tibame.group1.admin.dto.ServiceHistoryReqDTO;
import com.tibame.group1.admin.dto.ServiceMessageDTO;
import com.tibame.group1.admin.service.ChatroomHistoryService;
import com.tibame.group1.db.entity.ServiceChatroomEntity;
import com.tibame.group1.db.repository.ServiceChatroomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Service
public class ChatroomHistoryServiceImpl implements ChatroomHistoryService {
    @Autowired private ServiceChatroomRepository serviceChatroomRepository;

    @Override
    public List<ServiceMessageDTO> getHistoryMessage(ServiceHistoryReqDTO reqDTO) {
        //        List<ServiceChatroomEntity> chatroomEntities = new
        // ArrayList<ServiceChatroomEntity>();
        String type = reqDTO.getType();
        if(type == null){
            return null;
        }
        List<ServiceChatroomEntity> ents = null;
        switch (type) {
            case "memberId":
                ents = serviceChatroomRepository.findHistoryCustomer(reqDTO.getMemberId());
                break;
            case "date":
                ents = serviceChatroomRepository.findHistoryCustomer(reqDTO.getDateEnd());
                break;
            case "month":
                ents =
                        serviceChatroomRepository.findHistoryCustomer(
                                reqDTO.getYear(), reqDTO.getMonth());
                break;

            case "memberIdAndDate":
                ents =
                        serviceChatroomRepository.findHistoryCustomer(
                                reqDTO.getMemberId(), reqDTO.getMemberId());
                break;
            case "memberIdAndMonth":
                ents =
                        serviceChatroomRepository.findHistoryCustomer(
                                reqDTO.getMemberId(), reqDTO.getYear(), reqDTO.getMonth());
                break;
            default:
                break;
        }
        if (ents == null) {
            return null;
        }
        List<ServiceMessageDTO> serviceMessageDTOList = new ArrayList<>();
        for (ServiceChatroomEntity ent : ents) {
            ServiceMessageDTO serviceMessageDTO = new ServiceMessageDTO();
            serviceMessageDTO.setServiceId(ent.getServiceId());
            serviceMessageDTO.setMessage(ent.getMessage());
            serviceMessageDTO.setDate(ent.getDate());
            serviceMessageDTO.setMessage_type(ent.getType());
            serviceMessageDTO.setMemberId(ent.getCustomerId());
            serviceMessageDTOList.add(serviceMessageDTO);
        }
        return serviceMessageDTOList;
    }
}
