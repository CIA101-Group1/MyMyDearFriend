package com.tibame.group1.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FriendInfoDTO {
    private Integer id;
    private String name;
    private String avatar;
    private String latestMessage;
}
