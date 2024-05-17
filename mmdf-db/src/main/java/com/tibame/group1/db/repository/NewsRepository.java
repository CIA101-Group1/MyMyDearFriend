package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.NewsEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Integer> {}
