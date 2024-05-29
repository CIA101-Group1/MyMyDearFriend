package com.tibame.group1.web.service;

import com.tibame.group1.common.exception.AuthorizationException;

public interface ChatroomAddFriendService {
    public void addFriend(String authorization,Integer memberId )throws AuthorizationException;
}
