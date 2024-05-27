package com.tibame.group1.web.service;

public interface RedisAndSQLConnectSevice {
    //WebSocket 使用者上線執行
    void redisGetSqlData(Integer memberId);
  
    //WebSocket 使用者下線執行
    void redisSetSqlData(String memberId);
    void deleteRedisData(String memberId);
}
