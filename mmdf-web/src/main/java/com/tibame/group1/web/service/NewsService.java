package com.tibame.group1.web.service;

import com.tibame.group1.db.entity.NewsEntity;

import java.util.List;

public interface NewsService {
    List<NewsEntity> findAllActiveNews();

    NewsEntity findNewsById(Integer newsId);

}
