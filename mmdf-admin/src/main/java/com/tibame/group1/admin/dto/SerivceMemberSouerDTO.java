package com.tibame.group1.admin.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
@Getter
@Setter
public class SerivceMemberSouerDTO {
    private String type;
    private List memberIdList;
}
