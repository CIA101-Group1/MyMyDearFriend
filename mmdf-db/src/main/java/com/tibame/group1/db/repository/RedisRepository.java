package com.tibame.group1.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tibame.group1.db.entity.ChatroomRedisEntity;
@Repository
public interface RedisRepository extends CrudRepository<ChatroomRedisEntity, Integer> {
    List<ChatroomRedisEntity> findByMemberId(Integer memberId);
    List<String> findByKey(String key);
}
