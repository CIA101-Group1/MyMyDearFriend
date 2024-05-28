package com.tibame.group1.web.service.impl;

import com.tibame.group1.db.entity.NewsEntity;
import com.tibame.group1.db.repository.NewsRepository;
import com.tibame.group1.web.service.NewsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired NewsRepository newsRepository;

    @Override
    public List<NewsEntity> findAllActiveNews() {
        List<NewsEntity> newsEntities = newsRepository.findByStatusEquals(1);
        newsEntities.stream()
                .filter(newsEntity -> newsEntity.getImage() != null)
                .forEach(
                        newsEntity -> {
                            byte[] image = newsEntity.getImage();
                            String base64 = Base64.getEncoder().encodeToString(image);
                            newsEntity.setImageBase64("data:image/png;base64," + base64);
                        });

        return newsEntities;
    }

    @Override
    public NewsEntity findNewsById(Integer newsId) {
        Optional<NewsEntity> newsEntity = newsRepository.findById(newsId);

        byte[] image = newsEntity.get().getImage();
        String base64 = Base64.getEncoder().encodeToString(image);
        newsEntity.get().setImageBase64("data:image/png;base64," + base64);

        return newsEntity.get();
    }
}
