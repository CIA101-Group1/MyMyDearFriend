package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.NewsEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<NewsEntity, Integer> {
    List<NewsEntity> findByStatusEquals(Integer status);
}
