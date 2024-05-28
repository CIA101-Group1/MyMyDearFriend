package com.tibame.group1.admin.service.impl;

import com.tibame.group1.admin.service.NewsService;
import com.tibame.group1.common.dto.web.NewsAddReqDTO;
import com.tibame.group1.common.exception.CheckRequestErrorException;
import com.tibame.group1.db.entity.NewsEntity;
import com.tibame.group1.db.repository.NewsRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class NewsServiceImpl implements NewsService {
    @Autowired private NewsRepository newsRepository;

    @Transactional(readOnly = true)
    @Override
    public List<NewsEntity> findAll() {
        return newsRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public NewsEntity findById(Integer productId) {
        Optional<NewsEntity> optional = newsRepository.findById(productId);
        return optional.orElse(null);
    }

    @Override
    public void add(NewsAddReqDTO req) throws IOException {
        NewsEntity news = new NewsEntity();
        news.setTitle(req.getTitle());
        news.setContent(req.getContent());
        news.setImage(req.getImage().getBytes());
        news.setStatus(1);
        news.setLastModified(Date.valueOf(LocalDate.now()));
        newsRepository.save(news);
    }

    @Override
    public void update(Integer newsId, NewsAddReqDTO req)
            throws CheckRequestErrorException, IOException {
        NewsEntity news =
                newsRepository
                        .findById(newsId)
                        .orElseThrow(() -> new CheckRequestErrorException("查無此最新消息"));
        news.setTitle(req.getTitle());
        news.setContent(req.getContent());
        news.setImage(req.getImage().getBytes());
        // news.setStatus(req.getStatus());
        news.setLastModified(Date.valueOf(LocalDate.now()));
        newsRepository.save(news);
    }

    @Override
    public void updateStatus(Integer newsId, Integer newStatus) throws CheckRequestErrorException {
        NewsEntity news =
                newsRepository
                        .findById(newsId)
                        .orElseThrow(() -> new CheckRequestErrorException("查無此最新消息"));
        news.setStatus(newStatus);
        news.setLastModified(Date.valueOf(LocalDate.now()));
        newsRepository.save(news);
    }
}
