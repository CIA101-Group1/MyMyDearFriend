package com.tibame.group1.web.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class MemberFriendsDTO {
    private String type;
    private Integer number;
    private List<FriendInfoDTO> friends;
}
