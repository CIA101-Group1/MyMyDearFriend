package com.tibame.group1.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatStateDTO {
    private String type;
    private Integer memberId;
    private List<Integer> memberkey;
    public ChatStateDTO(String type, Integer memberId, List<Integer> memberkey) {
        this.type = type;
        this.memberId = memberId;
        this.memberkey = memberkey;
    }
}
