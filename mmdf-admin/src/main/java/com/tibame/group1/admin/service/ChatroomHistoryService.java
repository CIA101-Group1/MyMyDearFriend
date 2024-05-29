package com.tibame.group1.admin.service;

import com.tibame.group1.admin.dto.ServiceHistoryReqDTO;
import com.tibame.group1.admin.dto.ServiceMessageDTO;

import java.sql.Timestamp;
import java.util.List;

public interface ChatroomHistoryService {
    List<ServiceMessageDTO> getHistoryMessage(ServiceHistoryReqDTO reqDTO);
}
